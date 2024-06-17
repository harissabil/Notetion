package com.harissabil.notetion.ui.screen.question_detail

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.canopas.lib.showcase.IntroShowcase
import com.canopas.lib.showcase.component.rememberIntroShowcaseState
import com.harissabil.notetion.ui.screen.question_detail.components.QuestionAndAnswerList
import com.harissabil.notetion.ui.screen.question_detail.components.QuestionDetailTopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuestionDetailScreen(
    modifier: Modifier = Modifier,
    noteId: Int,
    viewModel: QuestionDetailViewModel = koinViewModel(),
    onNavigateUp: () -> Unit,
    onNoteDetail: (noteId: Int) -> Unit,
    onStartQuiz: (noteId: Int) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(key1 = true) {
        if (noteId != 0) viewModel.loadQuestionsWithAnswers(noteId)
    }

    IntroShowcase(
        showIntroShowCase = !viewModel.isShowIntroQuestionCompleted,
        onShowCaseCompleted = viewModel::onIntroQuestionCompleted,
        dismissOnClickOutside = true,
        state = rememberIntroShowcaseState()
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                QuestionDetailTopAppBar(
                    title = state.noteWithQuestionsAndAnswers?.note?.title,
                    onNavigateUp = onNavigateUp,
                    onNavigateToNote = { onNoteDetail(noteId) },
                    onStartQuiz = { onStartQuiz(noteId) },
                )
            }
        ) { innerPadding ->
            state.noteWithQuestionsAndAnswers?.let {
                QuestionAndAnswerList(
                    modifier = modifier.padding(innerPadding),
                    lazyListState = lazyListState,
                    questionsWithAnswers = it.questions,
                )
            }
        }
    }
}