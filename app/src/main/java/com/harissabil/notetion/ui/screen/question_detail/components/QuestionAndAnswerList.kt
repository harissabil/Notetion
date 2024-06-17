package com.harissabil.notetion.ui.screen.question_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.harissabil.notetion.data.local.entity.QuestionWithAnswers
import com.harissabil.notetion.ui.theme.spacing

@Composable
fun QuestionAndAnswerList(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    questionsWithAnswers: List<QuestionWithAnswers>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        state = lazyListState,
        contentPadding = PaddingValues(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(
            items = questionsWithAnswers,
            key = { _, item -> item.question.questionId }
        ) { _, questionWithAnswers ->
            QuestionAndAnswerItem(
                questionWithAnswers = questionWithAnswers,
                modifier = Modifier.animateItem()
            )
        }
    }
}