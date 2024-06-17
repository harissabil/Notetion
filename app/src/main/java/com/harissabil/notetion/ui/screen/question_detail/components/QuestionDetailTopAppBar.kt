package com.harissabil.notetion.ui.screen.question_detail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.canopas.lib.showcase.IntroShowcaseScope
import com.canopas.lib.showcase.component.ShowcaseStyle
import com.harissabil.notetion.ui.components.IntroShowCaseContent
import com.harissabil.notetion.ui.components.NoteBasicTooltip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroShowcaseScope.QuestionDetailTopAppBar(
    modifier: Modifier = Modifier,
    title: String?,
    onNavigateUp: () -> Unit,
    onNavigateToNote: () -> Unit,
    onStartQuiz: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            if (title != null) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title,
                    onValueChange = {},
                    singleLine = true,
                    textStyle = MaterialTheme.typography.titleLarge,
                    colors = TextFieldDefaults.colors().copy(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    ),
                    readOnly = true,
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Navigate up"
                )
            }
        },
        actions = {
            NoteBasicTooltip(
                tooltipState = rememberTooltipState(),
                text = "Navigate to note",
            ) {
                IconButton(
                    onClick = onNavigateToNote,
                    modifier = Modifier.introShowCaseTarget(
                        index = 0,
                        style = ShowcaseStyle.Default.copy(
                            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                            backgroundAlpha = 0.98f,
                            targetCircleColor = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    ) {
                        IntroShowCaseContent(
                            title = "Navigate to Note",
                            description = "Navigate to the Note Screen containing the notes from which the questions were generated."
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Description,
                        contentDescription = "Navigate to note"
                    )
                }
            }
            NoteBasicTooltip(
                tooltipState = rememberTooltipState(),
                text = "Start quiz",
            ) {
                IconButton(
                    onClick = onStartQuiz,
                    modifier = Modifier.introShowCaseTarget(
                        index = 1,
                        style = ShowcaseStyle.Default.copy(
                            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                            backgroundAlpha = 0.98f,
                            targetCircleColor = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    ) {
                        IntroShowCaseContent(
                            title = "Start Quiz",
                            description = "Start the quiz to test your knowledge on the note."
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Quiz,
                        contentDescription = "Start quiz"
                    )
                }
            }
        }
    )
}