package com.harissabil.notetion.ui.screen.questions

import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.harissabil.notetion.data.local.entity.NoteWithQuestionsAndAnswers
import com.harissabil.notetion.ui.components.NoteAlertDialog
import com.harissabil.notetion.ui.screen.questions.components.QuestionLazyStaggeredGrid
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuestionsScreen(
    modifier: Modifier = Modifier,
    query: String,
    viewModel: QuestionsViewModel = koinViewModel(),
    onQuestionClick: (noteId: Int) -> Unit,
    onStartQuizClick: (noteId: Int) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val lazyStaggeredGrid = rememberLazyStaggeredGridState()

    var questionToDelete by remember { mutableStateOf<NoteWithQuestionsAndAnswers?>(null) }
    var isDeleteDialogVisible by remember { mutableStateOf(false) }

    QuestionLazyStaggeredGrid(
        modifier = modifier,
        state = lazyStaggeredGrid,
        notes = state.noteWithQuestionsAndAnswers.reversed().filter {
            it.note.title?.contains(query, ignoreCase = true) ?:
            it.note.content.contains(query, ignoreCase = true)
        },
        onQuestionClick = onQuestionClick,
        onDeleteClick = { questionToDelete = it; isDeleteDialogVisible = true },
        onStartQuizClick = onStartQuizClick
    )

    if (isDeleteDialogVisible && questionToDelete != null) {
        NoteAlertDialog(
            title = "Confirm Delete Note",
            text = "Are you sure you want to delete this note?",
            onDismissRequest = { isDeleteDialogVisible = false; questionToDelete = null },
            confirmText = "Delete",
            dismissText = "Cancel",
            onConfirm = {
                viewModel.deleteQuestionsWithAnswers(questionToDelete!!)
                isDeleteDialogVisible = false
                questionToDelete = null
            },
            onDismiss = { isDeleteDialogVisible = false; questionToDelete = null },
        )
    }
}