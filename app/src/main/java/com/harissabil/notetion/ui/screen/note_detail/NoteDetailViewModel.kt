package com.harissabil.notetion.ui.screen.note_detail

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.type.content
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.harissabil.notetion.data.datastore.intro.IntroManager
import com.harissabil.notetion.data.gemini.GeminiClient
import com.harissabil.notetion.data.local.NoteRepository
import com.harissabil.notetion.data.local.entity.Note
import com.harissabil.notetion.domain.SaveGeneratedQuestion
import com.mohamedrejeb.richeditor.model.RichTextState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

class NoteDetailViewModel(
    private val geminiClient: GeminiClient,
    private val noteRepository: NoteRepository,
    private val saveGeneratedQuestion: SaveGeneratedQuestion,
    private val introManager: IntroManager,
) : ViewModel() {

    private val _state = MutableStateFlow(NoteDetailState())
    val state: StateFlow<NoteDetailState> = _state.asStateFlow()

    var isShowIntroNoteCompleted by mutableStateOf(false)
        private set

    init {
        getShowIntroNoteCompleted()
    }

    private fun getShowIntroNoteCompleted() = introManager.getShowIntroNoteCompleted().onEach {
        isShowIntroNoteCompleted = it
    }.launchIn(viewModelScope)

    fun onIntroNoteCompleted() = viewModelScope.launch {
        introManager.setShowIntroNoteCompleted(showIntroNoteCompleted = true)
    }

    fun loadNoteById(noteId: Int) = viewModelScope.launch {
        val noteWithQuestionsAndAnswers =
            noteRepository.getNoteWithQuestionsAndAnswersByNoteId(noteId)
        _state.value = _state.value.copy(
            id = noteWithQuestionsAndAnswers.note.noteId,
            title = noteWithQuestionsAndAnswers.note.title ?: "",
            content = RichTextState().setMarkdown(noteWithQuestionsAndAnswers.note.content),
            savedNote = noteWithQuestionsAndAnswers.note,
            questionsWithAnswers = noteWithQuestionsAndAnswers.questions
        )
    }

    fun onTitleChange(title: String) {
        _state.value = _state.value.copy(title = title)
    }

    fun onScanFromCamera(
        context: Context,
        imageBitmap: Bitmap,
    ) {
        if (_state.value.isTextCorrecting || _state.value.isTextRecognizing) return

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val image: InputImage = try {
            InputImage.fromBitmap(imageBitmap, 0)
        } catch (e: IOException) {
            Toast.makeText(context, "Failed to load image.", Toast.LENGTH_SHORT).show()
            return
        }

        _state.update {
            it.copy(isTextRecognizing = true)
        }

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                // Process text recognition result
                val resultText = visionText.text
                _state.update {
                    it.copy(
                        content = _state.value.content.setMarkdown((_state.value.content.toMarkdown() + resultText).trim()),
                        isTextRecognizing = false
                    )
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to recognize text.", Toast.LENGTH_SHORT).show()
                _state.update {
                    it.copy(isTextRecognizing = false)
                }
            }
    }

    fun onTextCorrector(context: Context) {
        if (_state.value.isTextCorrecting
            || _state.value.isTextRecognizing
            || _state.value.isGeneratingQuestion
        ) return

        if (_state.value.content.toMarkdown().isBlank()
            || _state.value.content.toMarkdown().length < 50
        ) {
            Toast.makeText(context, "Text is too short.", Toast.LENGTH_SHORT).show()
            return
        }

        _state.value = _state.value.copy(isTextCorrecting = true)

        Toast.makeText(context, "Text is being corrected...", Toast.LENGTH_SHORT).show()

        val generativeModel = geminiClient.geneminiProVisionModel

        val inputContent = content {
            text("Please correct the following text for any spelling and grammatical errors, and slightly paraphrase it while keeping the original language and the markdown format:\n")
            text(_state.value.content.toMarkdown())
        }

        viewModelScope.launch {
            try {
                val response = generativeModel.generateContent(inputContent)
                _state.update {
                    it.copy(
                        content = _state.value.content.setMarkdown(response.text.toString().trim()),
                        isTextCorrecting = false
                    )
                }
                Toast.makeText(context, "Text has been corrected.", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Timber.e(e)
                _state.update {
                    it.copy(isTextCorrecting = false)
                }
                Toast.makeText(context, "Failed to correct text.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onGenerateQuestion(context: Context) {
        if (_state.value.isTextCorrecting
            || _state.value.isTextRecognizing
            || _state.value.isGeneratingQuestion
        ) return

        if (_state.value.content.toMarkdown().isBlank()
            || _state.value.content.toMarkdown().length < 100
        ) {
            Toast.makeText(context, "Text is too short.", Toast.LENGTH_SHORT).show()
            return
        }

        _state.value = _state.value.copy(isGeneratingQuestion = true)

        Toast.makeText(context, "Question is being generated...", Toast.LENGTH_SHORT).show()

        if (_state.value.id == null) {
            saveNote(context)
        }

        val generativeModel = geminiClient.geneminiProVisionModel

        val inputContent = content {
            text(
                "Generate a JSON string representation of at least 10 questions, each with four possible answers, based on the following content. Ensure the JSON structure correctly represents a list of questions with their answers, where each answer has a field indicating if it is correct. The questions and answers must be in the same language as the content provided. The questions should be context-specific, detailed, and relevant to the content. If the content is not sufficient to generate 10 questions, generate as many as appropriate based on the content. If the content is possible to generate more than 10 questions, generate as many as possible." +
                        "\n" +
                        "Here is an example of the JSON structure we want:\n" +
                        "\n" +
                        "[\n" +
                        "  {\n" +
                        "    \"question\": {\n" +
                        "      \"questionId\": 0,\n" +
                        "      \"noteOriginId\": 0,\n" +
                        "      \"questionText\": \"What is the main topic discussed in the note?\",\n" +
                        "      \"createdAt\": null\n" +
                        "    },\n" +
                        "    \"answers\": [\n" +
                        "      {\n" +
                        "        \"answerId\": 0,\n" +
                        "        \"questionOriginId\": 0,\n" +
                        "        \"answerText\": \"The main topic is the importance of recycling.\",\n" +
                        "        \"isCorrect\": true,\n" +
                        "        \"createdAt\": null\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"answerId\": 1,\n" +
                        "        \"questionOriginId\": 0,\n" +
                        "        \"answerText\": \"The main topic is the benefits of exercise.\",\n" +
                        "        \"isCorrect\": false,\n" +
                        "        \"createdAt\": null\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"answerId\": 2,\n" +
                        "        \"questionOriginId\": 0,\n" +
                        "        \"answerText\": \"The main topic is the history of computing.\",\n" +
                        "        \"isCorrect\": false,\n" +
                        "        \"createdAt\": null\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"answerId\": 3,\n" +
                        "        \"questionOriginId\": 0,\n" +
                        "        \"answerText\": \"The main topic is the effects of global warming.\",\n" +
                        "        \"isCorrect\": false,\n" +
                        "        \"createdAt\": null\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"question\": {\n" +
                        "      \"questionId\": 1,\n" +
                        "      \"noteOriginId\": 0,\n" +
                        "      \"questionText\": \"What is one benefit of recycling mentioned in the note?\",\n" +
                        "      \"createdAt\": null\n" +
                        "    },\n" +
                        "    \"answers\": [\n" +
                        "      {\n" +
                        "        \"answerId\": 4,\n" +
                        "        \"questionOriginId\": 1,\n" +
                        "        \"answerText\": \"It reduces waste in landfills.\",\n" +
                        "        \"isCorrect\": true,\n" +
                        "        \"createdAt\": null\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"answerId\": 5,\n" +
                        "        \"questionOriginId\": 1,\n" +
                        "        \"answerText\": \"It increases energy consumption.\",\n" +
                        "        \"isCorrect\": false,\n" +
                        "        \"createdAt\": null\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"answerId\": 6,\n" +
                        "        \"questionOriginId\": 1,\n" +
                        "        \"answerText\": \"It decreases the quality of products.\",\n" +
                        "        \"isCorrect\": false,\n" +
                        "        \"createdAt\": null\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"answerId\": 7,\n" +
                        "        \"questionOriginId\": 1,\n" +
                        "        \"answerText\": \"It harms the environment.\",\n" +
                        "        \"isCorrect\": false,\n" +
                        "        \"createdAt\": null\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }\n" +
                        "  // More questions follow the same structure\n" +
                        "]\n"
            )
            text(_state.value.content.toMarkdown())
        }

        viewModelScope.launch {
            try {
                val response = generativeModel.generateContent(inputContent)
                val lines = response.text.toString().trim().lines()
                val fileteredLines = lines.subList(1, lines.size - 1)
                val result = fileteredLines.joinToString("\n")
                Timber.tag("Generated Question").d(result)

                val noteWithQuestionsAndAnswers = saveGeneratedQuestion(
                    noteId = _state.value.id!!,
                    jsonString = result
                )

                _state.update {
                    it.copy(
                        isGeneratingQuestion = false,
                        questionsWithAnswers = noteWithQuestionsAndAnswers
                    )
                }

                Toast.makeText(context, "Question has been generated.", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Timber.e(e)
                _state.update { it.copy(isGeneratingQuestion = false) }
                Toast.makeText(context, "Failed to generate question.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun saveNote(context: Context) = viewModelScope.launch {

        if (_state.value.isNoteBlank()) return@launch

        if (!_state.value.isNoteModified()) return@launch

        if (_state.value.id != null) {
            noteRepository.updateNote(
                Note(
                    noteId = _state.value.id!!,
                    title = state.value.title.trim(),
                    content = _state.value.content.toMarkdown()
                )
            )

            _state.update {
                it.copy(
                    savedNote = Note(
                        noteId = _state.value.id!!,
                        title = _state.value.title.trim(),
                        content = _state.value.content.toMarkdown()
                    )
                )
            }

            Toast.makeText(context, "Note has been updated.", Toast.LENGTH_SHORT).show()
            return@launch
        }

        val noteId = noteRepository.insertNote(
            Note(
                title = _state.value.title.trim(),
                content = _state.value.content.toMarkdown()
            )
        )

        _state.update {
            it.copy(
                id = noteId.toInt(),
                savedNote = Note(
                    noteId = noteId.toInt(),
                    title = _state.value.title.trim(),
                    content = _state.value.content.toMarkdown()
                )
            )
        }

        Toast.makeText(context, "Note has been saved.", Toast.LENGTH_SHORT).show()
    }
}