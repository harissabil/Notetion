package com.harissabil.notetion.ui.screen.notes.components

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
import com.harissabil.notetion.data.local.entity.Note
import com.harissabil.notetion.ui.components.NoItemAlert
import com.harissabil.notetion.ui.theme.spacing

@Composable
fun NoteLazyStaggeredGrid(
    modifier: Modifier = Modifier,
    state: LazyStaggeredGridState,
    notes: List<Note>?,
    onItemClick: (noteId: Int) -> Unit,
    onDeleteItemClick: (note: Note) -> Unit,
) {
    if (!notes.isNullOrEmpty()) {
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
            if (notes.isNotEmpty()) {
                items(items = notes, key = { it.noteId }) { note ->
                    NoteItem(
                        note = note,
                        onClick = onItemClick,
                        onDeleteClick = onDeleteItemClick,
                        modifier = Modifier.animateItem()
                    )
                }
            }
        }
    } else {
        NoItemAlert(text = "No notes yet!")
    }
}