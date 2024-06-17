package com.harissabil.notetion.domain

import com.google.gson.Gson
import com.harissabil.notetion.data.local.NoteRepository
import com.harissabil.notetion.data.local.entity.QuestionWithAnswers
import kotlinx.datetime.Clock

class SaveGeneratedQuestion(
    private val noteRepository: NoteRepository,
) {
    suspend operator fun invoke(
        noteId: Int,
        jsonString: String,
    ): List<QuestionWithAnswers> {
        val questionsWithAnswersExist =
            noteRepository.getNoteWithQuestionsAndAnswersByNoteId(noteId)

        if (questionsWithAnswersExist.questions.isNotEmpty()) {
            noteRepository.deleteQuestionsByNoteId(
                questionsWithAnswersExist.questions.first().question.noteOriginId
            )
        }

        val gson = Gson()
        val questionsWithAnswers =
            gson.fromJson(jsonString, Array<QuestionWithAnswers>::class.java).toList()
        questionsWithAnswers.forEach { questionWithAnswer ->
            val question = questionWithAnswer.question.copy(
                questionId = 0,
                noteOriginId = noteId,
                createdAt = Clock.System.now()
            )
            val questionId = noteRepository.insertQuestion(question)
            val answers = questionWithAnswer.answers.map { answer ->
                answer.copy(
                    answerId = 0,
                    questionOriginId = questionId.toInt(),
                    createdAt = Clock.System.now()
                )
            }
            noteRepository.insertAllAnswers(answers)
        }

        return questionsWithAnswers
    }
}