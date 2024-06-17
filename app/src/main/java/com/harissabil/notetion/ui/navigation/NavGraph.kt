package com.harissabil.notetion.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.harissabil.notetion.ui.screen.about.AboutScreen
import com.harissabil.notetion.ui.screen.home.HomeScreen
import com.harissabil.notetion.ui.screen.note_detail.NoteDetailScreen
import com.harissabil.notetion.ui.screen.question_detail.QuestionDetailScreen
import com.harissabil.notetion.ui.screen.quiz.QuizScreen
import com.harissabil.notetion.ui.screen.settings.SettingsScreen
import com.harissabil.notetion.ui.utils.slideContainerAnimationComposable

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home,
        modifier = modifier
    ) {
        composable<Screen.Home> {
            HomeScreen(
                onAddNoteClick = { navController.navigate(Screen.NoteDetail()) },
                onNoteClick = { noteId -> navController.navigate(Screen.NoteDetail(noteId)) },
                onQuestionClick = { noteId -> navController.navigate(Screen.QuestionDetail(noteId)) },
                onStartQuizClick = { noteId -> navController.navigate(Screen.Quiz(noteId)) },
                onSettingsClick = { navController.navigate(Screen.Settings) },
                onAboutClick = { navController.navigate(Screen.About) }
            )
        }
        slideContainerAnimationComposable<Screen.NoteDetail> {
            val args = it.toRoute<Screen.NoteDetail>()
            NoteDetailScreen(
                noteId = args.noteId,
                onNavigateUp = { navController.navigateUp() },
                onNavigateToQuestions = { noteId ->
                    navController.navigate(
                        Screen.QuestionDetail(
                            noteId
                        )
                    )
                },
            )
        }
        slideContainerAnimationComposable<Screen.QuestionDetail> {
            val args = it.toRoute<Screen.QuestionDetail>()
            QuestionDetailScreen(
                noteId = args.noteId,
                onNavigateUp = { navController.navigateUp() },
                onNoteDetail = { noteId -> navController.navigate(Screen.NoteDetail(noteId)) },
                onStartQuiz = { noteId -> navController.navigate(Screen.Quiz(noteId)) }
            )
        }
        composable<Screen.Quiz> {
            val args = it.toRoute<Screen.Quiz>()
            QuizScreen(
                noteId = args.noteId,
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable<Screen.Settings> {
            SettingsScreen(
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable<Screen.About> {
            AboutScreen(
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}