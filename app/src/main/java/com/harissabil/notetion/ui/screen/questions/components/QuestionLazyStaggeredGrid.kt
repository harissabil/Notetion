package com.harissabil.notetion.ui.screen.questions.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harissabil.notetion.data.local.entity.NoteWithQuestionsAndAnswers
import com.harissabil.notetion.ui.components.NoItemAlert
import com.harissabil.notetion.ui.theme.spacing

@Composable
fun QuestionLazyStaggeredGrid(
    modifier: Modifier = Modifier,
    state: LazyStaggeredGridState,
    notes: List<NoteWithQuestionsAndAnswers>?,
    onQuestionClick: (noteId: Int) -> Unit,
    onStartQuizClick: (noteId: Int) -> Unit,
    onDeleteClick: (note: NoteWithQuestionsAndAnswers) -> Unit,
) {
    val notesWithQuestions = notes?.filter { it.questions.isNotEmpty() }
        ?.sortedByDescending { it.questions.first().question.createdAt }

    if (!notesWithQuestions.isNullOrEmpty()) {
        LazyVerticalStaggeredGrid(
            state = state,
            columns = StaggeredGridCells.Adaptive(180.dp),
            modifier = Modifier
                .fillMaxSize()
                .then(modifier),
            contentPadding = PaddingValues(MaterialTheme.spacing.medium),
            verticalItemSpacing = MaterialTheme.spacing.medium,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        ) {
            items(
                items = notesWithQuestions,
                key = { it.note.noteId }) { noteWithQuestionsAndAnswers ->
                QuestionItem(
                    note = noteWithQuestionsAndAnswers.note,
                    questions = noteWithQuestionsAndAnswers.questions,
                    onClick = onQuestionClick,
                    onStartQuizClick = onStartQuizClick,
                    onDeleteClick = { onDeleteClick(noteWithQuestionsAndAnswers) },
                    modifier = Modifier.animateItem()
                )
            }
        }
    } else {
        NoItemAlert(text = "No questions yet!")
    }
}