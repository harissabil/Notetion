package com.harissabil.notetion.ui.screen.question_detail.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.harissabil.notetion.data.local.entity.Answer
import com.harissabil.notetion.data.local.entity.Question
import com.harissabil.notetion.data.local.entity.QuestionWithAnswers
import com.harissabil.notetion.ui.theme.NotetionTheme
import com.harissabil.notetion.ui.theme.spacing

@Composable
fun QuestionAndAnswerItem(
    modifier: Modifier = Modifier,
    questionWithAnswers: QuestionWithAnswers,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Card(shape = RectangleShape) {
            Text(
                text = questionWithAnswers.question.questionText,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium)
            )
        }
        OutlinedCard(shape = RectangleShape) {
            Text(
                text = questionWithAnswers.answers.find { it.isCorrect }!!.answerText,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium)
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun QuestionAndAnswerItemPreview() {
    NotetionTheme {
        QuestionAndAnswerItem(
            questionWithAnswers = QuestionWithAnswers(
                question = Question(
                    questionId = 1,
                    questionText = "What is the capital of Indonesia?",
                    noteOriginId = 1
                ),
                answers = listOf(
                    Answer(
                        answerId = 1,
                        questionOriginId = 1,
                        answerText = "Jakarta",
                        isCorrect = true
                    ),
                    Answer(
                        answerId = 2,
                        questionOriginId = 1,
                        answerText = "Bandung",
                        isCorrect = false
                    ),
                    Answer(
                        answerId = 3,
                        questionOriginId = 1,
                        answerText = "Surabaya",
                        isCorrect = false
                    ),
                    Answer(
                        answerId = 4,
                        questionOriginId = 1,
                        answerText = "Bali",
                        isCorrect = false
                    )
                )
            )
        )
    }
}