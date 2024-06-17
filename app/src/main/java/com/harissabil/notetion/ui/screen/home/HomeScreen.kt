package com.harissabil.notetion.ui.screen.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.canopas.lib.showcase.IntroShowcase
import com.canopas.lib.showcase.component.rememberIntroShowcaseState
import com.harissabil.notetion.ui.screen.home.components.AddFab
import com.harissabil.notetion.ui.screen.home.components.HomeNavDrawer
import com.harissabil.notetion.ui.screen.home.components.SearchTopAppBar
import com.harissabil.notetion.ui.screen.notes.NotesScreen
import com.harissabil.notetion.ui.screen.questions.QuestionsScreen
import com.harissabil.notetion.ui.theme.NoRippleTheme
import com.harissabil.notetion.ui.theme.spacing
import com.harissabil.notetion.ui.utils.AnimatedFabVisibility
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onAddNoteClick: () -> Unit,
    onNoteClick: (noteId: Int) -> Unit,
    onQuestionClick: (noteId: Int) -> Unit,
    onStartQuizClick: (noteId: Int) -> Unit,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val tabTitles by rememberSaveable { mutableStateOf(listOf("Notes", "Questions")) }
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabTitles.size })

    val keyboardController = LocalSoftwareKeyboardController.current

    var query by rememberSaveable { mutableStateOf("") }

    BackHandler(enabled = drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }

    IntroShowcase(
        showIntroShowCase = !viewModel.isShowIntroHomeCompleted,
        onShowCaseCompleted = viewModel::onIntroHomeCompleted,
        dismissOnClickOutside = true,
        state = rememberIntroShowcaseState()
    ) {
        Scaffold { outerPadding ->
            HomeNavDrawer(
                modifier = modifier
                    .fillMaxSize()
                    .padding(outerPadding),
                onSettingsClick = { scope.launch { drawerState.close() }; onSettingsClick() },
                onAboutClick = { scope.launch { drawerState.close() }; onAboutClick() },
                drawerState = drawerState,
                content = {
                    Scaffold(
                        topBar = {
                            SearchTopAppBar(
                                modifier = Modifier.padding(
                                    horizontal = MaterialTheme.spacing.medium,
                                    vertical = MaterialTheme.spacing.small
                                ),
                                query = query,
                                onQueryChange = { query = it },
                                onMenuClick = {
                                    scope.launch {
                                        if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                        keyboardController?.hide()
                                    }
                                }
                            )
                        },
                        floatingActionButton = {
                            AnimatedFabVisibility(visible = pagerState.currentPage == 0) {
                                AddFab(
                                    modifier = Modifier.padding(MaterialTheme.spacing.small),
                                    onClick = onAddNoteClick
                                )
                            }
                        },
                        floatingActionButtonPosition = FabPosition.EndOverlay,
                        contentWindowInsets = WindowInsets(0, 0, 0, 0),
                    ) { innerPadding ->
                        HomeContent(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(innerPadding),
                            pagerState = pagerState,
                            tabTitles = tabTitles,
                            notesScreen = {
                                NotesScreen(
                                    query = query,
                                    onNoteClick = onNoteClick,
                                )
                            },
                            questionsScreen = {
                                QuestionsScreen(
                                    query = query,
                                    onQuestionClick = onQuestionClick,
                                    onStartQuizClick = onStartQuizClick
                                )
                            }
                        )
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    tabTitles: List<String>,
    notesScreen: @Composable () -> Unit,
    questionsScreen: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        PrimaryTabRow(
            selectedTabIndex = pagerState.currentPage
        ) {
            tabTitles.forEachIndexed { index, title ->
                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(text = title) },
                        unselectedContentColor = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
        HorizontalPager(state = pagerState) { index ->
            when (index) {
                0 -> {
                    notesScreen()
                }

                1 -> {
                    questionsScreen()
                }
            }
        }
    }
}