package com.harissabil.notetion.data.local

import com.harissabil.notetion.data.local.entity.Answer
import com.harissabil.notetion.data.local.entity.Note
import com.harissabil.notetion.data.local.entity.Question
import com.harissabil.notetion.data.local.room.NoteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock

class NoteRepository(private val noteDao: NoteDao) {

    // Note related operations
    suspend fun insertNote(note: Note): Long = noteDao.insertNote(note)

    suspend fun updateNote(note: Note) = noteDao.updateNote(
        note.copy(
            updatedAt = Clock.System.now()
        )
    )

    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)

    suspend fun getNoteById(id: Int): Note = noteDao.getNoteById(id)

    fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    fun getNotesWithQuestionsAndAnswer() = noteDao.getNotesWithQuestionsAndAnswer()

    suspend fun getNoteWithQuestionsAndAnswersByNoteId(noteId: Int) =
        noteDao.getNoteWithQuestionsAndAnswersByNoteId(noteId)

    // Question related operations
    suspend fun insertQuestion(question: Question) =
        noteDao.insertQuestion(question)

    suspend fun deleteQuestionsByNoteId(noteId: Int) = noteDao.deleteQuestionsByNoteId(noteId)

    // Answer related operations
    suspend fun insertAllAnswers(answers: List<Answer>) = noteDao.insertAllAnswers(answers)
}
