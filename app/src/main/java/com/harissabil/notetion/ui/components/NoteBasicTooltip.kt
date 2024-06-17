package com.harissabil.notetion.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.harissabil.notetion.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteBasicTooltip(
    modifier: Modifier = Modifier,
    tooltipState: TooltipState,
    text: String,
    content: @Composable () -> Unit,
) {
    TooltipBox(
        modifier = modifier,
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(
            spacingBetweenTooltipAndAnchor = MaterialTheme.spacing.medium + MaterialTheme.spacing.small
        ),
        tooltip = {
            PlainTooltip {
                Text(text = text)
            }
        },
        state = tooltipState
    ) {
        content()
    }
}