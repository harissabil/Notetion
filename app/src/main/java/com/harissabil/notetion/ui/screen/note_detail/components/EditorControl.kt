package com.harissabil.notetion.ui.screen.note_detail.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatAlignLeft
import androidx.compose.material.icons.automirrored.filled.FormatAlignRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FormatAlignCenter
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatColorText
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.harissabil.notetion.ui.theme.NotetionTheme
import com.harissabil.notetion.ui.theme.spacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditorControl(
    modifier: Modifier = Modifier,
    onBoldClick: () -> Unit,
    onItalicClick: () -> Unit,
    onUnderlineClick: () -> Unit,
    onTitleClick: () -> Unit,
    onSubtitleClick: () -> Unit,
    onTextColorClick: () -> Unit,
    onStartAlignClick: () -> Unit,
    onEndAlignClick: () -> Unit,
    onCenterAlignClick: () -> Unit,
    onCloseEditorControl: () -> Unit,
) {
    var boldSelected by rememberSaveable { mutableStateOf(false) }
    var italicSelected by rememberSaveable { mutableStateOf(false) }
    var underlineSelected by rememberSaveable { mutableStateOf(false) }
    var titleSelected by rememberSaveable { mutableStateOf(false) }
    var subtitleSelected by rememberSaveable { mutableStateOf(false) }
    var textColorSelected by rememberSaveable { mutableStateOf(false) }
    var alignmentSelected by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        HorizontalDivider()
        Spacer(modifier = Modifier.padding(top = MaterialTheme.spacing.small))
        IconButton(
            onClick = onCloseEditorControl,
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)
        ) {
            Icon(imageVector = Icons.Default.Clear, contentDescription = "Close Editor Control")
        }
        Spacer(modifier = Modifier.padding(top = MaterialTheme.spacing.medium))
        FlowRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.spacing.medium)
                .padding(horizontal = MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.medium,
                Alignment.CenterHorizontally
            ),
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.medium
            ),
            maxItemsInEachRow = 6
        ) {
            ControlWrapper(
                selected = boldSelected,
                onChangeClick = { boldSelected = it },
                onClick = onBoldClick,
                icon = Icons.Default.FormatBold,
                contentDescription = "Bold Control",
            )
            ControlWrapper(
                selected = italicSelected,
                onChangeClick = { italicSelected = it },
                onClick = onItalicClick,
                icon = Icons.Default.FormatItalic,
                contentDescription = "Italic Control",
            )
            ControlWrapper(
                selected = underlineSelected,
                onChangeClick = { underlineSelected = it },
                onClick = onUnderlineClick,
                icon = Icons.Default.FormatUnderlined,
                contentDescription = "Underline Control",
            )
            ControlWrapper(
                selected = titleSelected,
                onChangeClick = { titleSelected = it },
                onClick = onTitleClick,
                icon = Icons.Default.Title,
                contentDescription = "Title Control",
            )
            ControlWrapper(
                selected = subtitleSelected,
                onChangeClick = { subtitleSelected = it },
                onClick = onSubtitleClick,
                icon = Icons.Default.FormatSize,
                contentDescription = "Subtitle Control",
            )
            ControlWrapper(
                selected = textColorSelected,
                onChangeClick = { textColorSelected = it },
                onClick = onTextColorClick,
                icon = Icons.Default.FormatColorText,
                contentDescription = "Text Color Control",
            )
            ControlWrapper(
                selected = alignmentSelected == 0,
                onChangeClick = { alignmentSelected = 0 },
                onClick = onStartAlignClick,
                icon = Icons.AutoMirrored.Filled.FormatAlignLeft,
                contentDescription = "Start Align Control",
            )
            ControlWrapper(
                selected = alignmentSelected == 1,
                onChangeClick = { alignmentSelected = 1 },
                onClick = onCenterAlignClick,
                icon = Icons.Default.FormatAlignCenter,
                contentDescription = "Center Align Control",
            )
            ControlWrapper(
                selected = alignmentSelected == 2,
                onChangeClick = { alignmentSelected = 2 },
                onClick = onEndAlignClick,
                icon = Icons.AutoMirrored.Filled.FormatAlignRight,
                contentDescription = "End Align Control",
            )
        }
        Spacer(modifier = Modifier.padding(top = MaterialTheme.spacing.small))
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EditorControlPreview() {
    NotetionTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxWidth()
        ) {
            EditorControl(
                onBoldClick = {},
                onItalicClick = {},
                onUnderlineClick = {},
                onTitleClick = {},
                onSubtitleClick = {},
                onTextColorClick = {},
                onStartAlignClick = {},
                onEndAlignClick = {},
                onCenterAlignClick = {},
                onCloseEditorControl = {}
            )
        }
    }
}