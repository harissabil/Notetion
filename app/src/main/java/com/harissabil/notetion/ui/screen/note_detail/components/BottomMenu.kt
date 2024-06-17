package com.harissabil.notetion.ui.screen.note_detail.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.canopas.lib.showcase.IntroShowcase
import com.canopas.lib.showcase.IntroShowcaseScope
import com.canopas.lib.showcase.component.ShowcaseStyle
import com.harissabil.notetion.ui.components.IntroShowCaseContent
import com.harissabil.notetion.ui.components.NoteRichTooltip
import com.harissabil.notetion.ui.theme.NotetionTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroShowcaseScope.BottomMenu(
    modifier: Modifier = Modifier,
    onOpenEditorControl: () -> Unit,
    onScanFromCamera: () -> Unit,
    onTextCorrector: () -> Unit,
    onGenerateQuestion: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val tooltipState = mapOf(
        "Editor Control" to rememberTooltipState(isPersistent = true),
        "Scan from Camera" to rememberTooltipState(isPersistent = true),
        "AI Text Corrector" to rememberTooltipState(isPersistent = true),
        "Generate Question" to rememberTooltipState(isPersistent = true),
    )

    Column {
        HorizontalDivider()
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .then(modifier),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            NoteRichTooltip(
                title = "Editor Control",
                text = "Choose from a variety of styling options to customize your text.",
                onGotItClick = {
                    scope.launch {
                        tooltipState.entries.forEach { (_, state) ->
                            state.dismiss()
                        }
                    }
                },
                tooltipState = tooltipState["Editor Control"]!!
            ) {
                IconButton(
                    onClick = onOpenEditorControl,
                    modifier = Modifier.introShowCaseTarget(
                        index = 0,
                        style = ShowcaseStyle.Default.copy(
                            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                            backgroundAlpha = 0.98f,
                            targetCircleColor = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    ) {
                        IntroShowCaseContent(
                            title = "Editor Control",
                            description = "Choose from a variety of styling options to customize your text."
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Title,
                        contentDescription = "Open editor control",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
            NoteRichTooltip(
                title = "Scan from Camera",
                text = "Scan a text from camera and insert it into the editor.",
                onGotItClick = {
                    scope.launch {
                        tooltipState.entries.forEach { (_, state) ->
                            state.dismiss()
                        }
                    }
                },
                tooltipState = tooltipState["Scan from Camera"]!!
            ) {
                IconButton(onClick = onScanFromCamera) {
                    Icon(
                        imageVector = Icons.Default.DocumentScanner,
                        contentDescription = "Scan from camera",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.introShowCaseTarget(
                            index = 1,
                            style = ShowcaseStyle.Default.copy(
                                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                                backgroundAlpha = 0.98f,
                                targetCircleColor = MaterialTheme.colorScheme.inverseOnSurface
                            )
                        ) {
                            IntroShowCaseContent(
                                title = "Scan from Camera",
                                description = "Scan a text from camera and insert it into the editor."
                            )
                        }
                    )
                }
            }
            NoteRichTooltip(
                title = "AI Text Corrector",
                text = "Correct your text using AI and improve your writing.",
                onGotItClick = {
                    scope.launch {
                        tooltipState.entries.forEach { (_, state) ->
                            state.dismiss()
                        }
                    }
                },
                tooltipState = tooltipState["AI Text Corrector"]!!
            ) {
                IconButton(onClick = onTextCorrector) {
                    Icon(
                        imageVector = Icons.Default.Spellcheck, // This is an example icon
                        contentDescription = "AI Text Corrector",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.introShowCaseTarget(
                            index = 2,
                            style = ShowcaseStyle.Default.copy(
                                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                                backgroundAlpha = 0.98f,
                                targetCircleColor = MaterialTheme.colorScheme.inverseOnSurface
                            )
                        ) {
                            IntroShowCaseContent(
                                title = "AI Text Corrector",
                                description = "Correct your text using AI and improve your writing."
                            )
                        }
                    )
                }
            }
            NoteRichTooltip(
                title = "Generate Question",
                text = "Generate a question from your text and test your knowledge.",
                onGotItClick = {
                    scope.launch {
                        tooltipState.entries.forEach { (_, state) ->
                            state.dismiss()
                        }
                    }
                },
                tooltipState = tooltipState["Generate Question"]!!
            ) {
                IconButton(onClick = onGenerateQuestion) {
                    Icon(
                        imageVector = Icons.Default.QuestionMark,
                        contentDescription = "Generate question",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.introShowCaseTarget(
                            index = 3,
                            style = ShowcaseStyle.Default.copy(
                                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                                backgroundAlpha = 0.98f,
                                targetCircleColor = MaterialTheme.colorScheme.inverseOnSurface
                            )
                        ) {
                            IntroShowCaseContent(
                                title = "Generate Question",
                                description = "Generate a question from your text and test your knowledge."
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BottomMenuPreview() {
    NotetionTheme {
        IntroShowcase(showIntroShowCase = false, onShowCaseCompleted = { /*TODO*/ }) {
            BottomMenu(
                onOpenEditorControl = {},
                onScanFromCamera = {},
                onTextCorrector = {},
                onGenerateQuestion = {}
            )
        }
    }
}