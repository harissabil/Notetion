package com.harissabil.notetion.ui.screen.home.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harissabil.notetion.ui.theme.NotetionTheme
import com.harissabil.notetion.ui.utils.AnimatedTrailingIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onMenuClick: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    DockedSearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null
                )
            }
        },
        placeholder = {
            Text(
                text = "Search in Notetion",
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        },
        trailingIcon = {
            Box(
                contentAlignment = Alignment.CenterStart
            ) {
                AnimatedTrailingIcon(
                    visible = query.isNotEmpty(),
                ) {
                    IconButton(
                        onClick = {
                            onQueryChange("")
                            keyboardController?.hide()
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear search query"
                            )
                        }
                    )
                }
            }
            AnimatedTrailingIcon(
                visible = query.isEmpty()
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            }
        },
        content = {}
    )
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun SearchTopAppBarPreview() {
    NotetionTheme {
        SearchTopAppBar(
            query = "Organisasi dan Arsiektur Komputer",
            onQueryChange = {},
            onMenuClick = {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}