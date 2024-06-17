package com.harissabil.notetion.ui.screen.notes

import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.harissabil.notetion.data.local.entity.Note
import com.harissabil.notetion.ui.components.NoteAlertDialog
import com.harissabil.notetion.ui.screen.notes.components.NoteLazyStaggeredGrid
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    query: String,
    viewModel: NotesViewModel = koinViewModel(),
    onNoteClick: (noteId: Int) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val lazyStaggeredGrid = rememberLazyStaggeredGridState()

    var noteToDelete by remember { mutableStateOf<Note?>(null) }
    var isDeleteDialogVisible by remember { mutableStateOf(false) }

    NoteLazyStaggeredGrid(
        modifier = modifier,
        state = lazyStaggeredGrid,
        notes = state.notes.reversed().filter {
            it.title?.contains(query, ignoreCase = true) ?:
            it.content.contains(query, ignoreCase = true)
        },
        onItemClick = onNoteClick,
        onDeleteItemClick = { noteToDelete = it; isDeleteDialogVisible = true }
    )

    if (isDeleteDialogVisible && noteToDelete != null) {
        NoteAlertDialog(
            title = "Confirm Delete Note",
            text = "Are you sure you want to delete this note?",
            onDismissRequest = { isDeleteDialogVisible = false; noteToDelete = null },
            confirmText = "Delete",
            dismissText = "Cancel",
            onConfirm = {
                viewModel.deleteNote(note = noteToDelete!!)
                isDeleteDialogVisible = false
                noteToDelete = null
            },
            onDismiss = { isDeleteDialogVisible = false; noteToDelete = null },
        )
    }
}