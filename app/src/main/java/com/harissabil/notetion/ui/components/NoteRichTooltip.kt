package com.harissabil.notetion.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteRichTooltip(
    modifier: Modifier = Modifier,
    tooltipState: TooltipState,
    title: String,
    text: String,
    onGotItClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    TooltipBox(
        modifier = modifier,
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            RichTooltip(
                title = { Text(text = title) },
                text = { Text(text = text) },
                action = {
                    TextButton(onClick = onGotItClick) {
                        Text("Got it")
                    }
                }
            )
        },
        state = tooltipState
    ) {
        content()
    }
}