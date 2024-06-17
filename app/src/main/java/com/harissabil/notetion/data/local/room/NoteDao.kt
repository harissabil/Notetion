package com.harissabil.notetion.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.harissabil.notetion.data.local.entity.Answer
import com.harissabil.notetion.data.local.entity.Note
import com.harissabil.notetion.data.local.entity.NoteWithQuestionsAndAnswers
import com.harissabil.notetion.data.local.entity.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    // Note related queries
    @Insert
    suspend fun insertNote(note: Note): Long

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note WHERE noteId = :id")
    suspend fun getNoteById(id: Int): Note

    @Query("SELECT * FROM note")
    fun getAllNotes(): Flow<List<Note>>

    @Transaction
    @Query("SELECT * FROM note")
    fun getNotesWithQuestionsAndAnswer(): Flow<List<NoteWithQuestionsAndAnswers>>

    @Transaction
    @Query("SELECT * FROM note WHERE noteId = :noteId")
    suspend fun getNoteWithQuestionsAndAnswersByNoteId(noteId: Int): NoteWithQuestionsAndAnswers

    // Question related queries
    @Insert
    suspend fun insertQuestion(question: Question): Long

//    @Query("SELECT * FROM question WHERE noteOriginId = :noteId")
//    suspend fun getQuestionsAndAnswersByNoteId(noteId: Int): List<QuestionWithAnswers>

    @Query("DELETE FROM question WHERE noteOriginId = :noteId")
    suspend fun deleteQuestionsByNoteId(noteId: Int)

    // Answer related queries
    @Insert
    suspend fun insertAllAnswers(answers: List<Answer>): List<Long>

//    @Query("SELECT * FROM answer WHERE questionOriginId = :questionId")
//    suspend fun getAnswersByQuestionId(questionId: Int): List<Answer>

    @Query("DELETE FROM answer WHERE questionOriginId = :questionId")
    suspend fun deleteAnswersByQuestionId(questionId: Int)
}
