package com.harissabil.notetion.ui.screen.note_detail.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.HelpCenter
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import com.canopas.lib.showcase.IntroShowcase
import com.canopas.lib.showcase.IntroShowcaseScope
import com.canopas.lib.showcase.component.ShowcaseStyle
import com.harissabil.notetion.ui.components.IntroShowCaseContent
import com.harissabil.notetion.ui.components.NoteBasicTooltip
import com.harissabil.notetion.ui.theme.NotetionTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroShowcaseScope.NoteDetailTopAppBar(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    title: String,
    onTitleChange: (String) -> Unit,
    isQuestionsAvailable: Boolean,
    onNavigateToQuestions: () -> Unit,
    isDoneEnabled: Boolean,
    onDone: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = onTitleChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.titleLarge,
                placeholder = {
                    Text(
                        text = "Title",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.alpha(0.5f)
                    )
                },
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
                )
            )
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
                text = "Navigate to questions",
            ) {
                IconButton(
                    onClick = onNavigateToQuestions,
                    enabled = isQuestionsAvailable,
                    modifier = Modifier.introShowCaseTarget(
                        index = 4,
                        style = ShowcaseStyle.Default.copy(
                            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                            backgroundAlpha = 0.98f,
                            targetCircleColor = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    ) {
                        IntroShowCaseContent(
                            title = "Navigate to Questions",
                            description = "Navigate to the Questions Screen that contains the questions generated from the note."
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.HelpCenter,
                        contentDescription = "Navigate to questions"
                    )
                }
            }
            NoteBasicTooltip(
                tooltipState = rememberTooltipState(),
                text = "Save note",
            ) {
                IconButton(
                    onClick = onDone,
                    enabled = isDoneEnabled
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save note"
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NoteDetailTopAppBarPreview() {
    NotetionTheme {
        IntroShowcase(showIntroShowCase = false, onShowCaseCompleted = { /*TODO*/ }) {
            NoteDetailTopAppBar(
                onNavigateUp = { },
                title = "",
                onTitleChange = { },
                isQuestionsAvailable = true,
                onNavigateToQuestions = {},
                onDone = {},
                isDoneEnabled = true
            )
        }
    }
}