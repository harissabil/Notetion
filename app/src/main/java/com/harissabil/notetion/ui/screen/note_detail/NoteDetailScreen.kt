package com.harissabil.notetion.ui.screen.note_detail

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.canopas.lib.showcase.IntroShowcase
import com.canopas.lib.showcase.component.rememberIntroShowcaseState
import com.harissabil.notetion.R
import com.harissabil.notetion.ui.screen.note_detail.components.BottomMenu
import com.harissabil.notetion.ui.screen.note_detail.components.CropperTopAppBar
import com.harissabil.notetion.ui.screen.note_detail.components.EditorControl
import com.harissabil.notetion.ui.screen.note_detail.components.NoteDetailTopAppBar
import com.harissabil.notetion.ui.theme.NoRippleTheme
import com.harissabil.notetion.ui.theme.spacing
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import com.mr0xf00.easycrop.CropError
import com.mr0xf00.easycrop.CropResult
import com.mr0xf00.easycrop.crop
import com.mr0xf00.easycrop.rememberImageCropper
import com.mr0xf00.easycrop.ui.ImageCropperDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    modifier: Modifier = Modifier,
    noteId: Int = 0,
    onNavigateUp: () -> Unit,
    onNavigateToQuestions: (noteId: Int) -> Unit,
    viewModel: NoteDetailViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val state by viewModel.state.collectAsState()

    val showKeyboard by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    val titleSize = MaterialTheme.typography.titleLarge.fontSize
    val subtitleSize = MaterialTheme.typography.bodyMedium.fontSize

    var isEditorControlsVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        if (noteId != 0) noteId.let { viewModel.loadNoteById(it) }
    }

    val directory = File(context.cacheDir, "images")
    val tempUri = remember { mutableStateOf<Uri?>(null) }
    val authority = stringResource(id = R.string.fileprovider)

    val imageCropper = rememberImageCropper()
    val cropState = imageCropper.cropState
    if (cropState != null) ImageCropperDialog(
        state = cropState,
        topBar = { CropperTopAppBar(cropState) }
    )

    // for takePhotoLauncher used
    fun getTempUri(): Uri? {
        directory.let {
            it.mkdirs()
            val file = File.createTempFile(
                "image_" + System.currentTimeMillis().toString(),
                ".jpg",
                it
            )

            return FileProvider.getUriForFile(
                context,
                authority,
                file
            )
        }
    }

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { _ ->
            tempUri.value?.let { imageUri ->
                scope.launch {
                    val result = imageCropper.crop(
                        uri = imageUri,
                        context = context
                    )
                    when (result) {
                        CropError.LoadingError -> {
                            tempUri.value = null
                        }

                        CropError.SavingError -> {
                            tempUri.value = null
                        }

                        CropResult.Cancelled -> {
                            tempUri.value = null
                        }

                        is CropResult.Success -> {
                            viewModel.onScanFromCamera(
                                context = context,
                                imageBitmap = result.bitmap.asAndroidBitmap(),
                            )
                        }
                    }
                }
            }
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted, launch takePhotoLauncher
            val tmpUri = getTempUri()
            tempUri.value = tmpUri
            tempUri.value?.let { takePhotoLauncher.launch(it) }
        } else {
            Toast.makeText(context, "Camera permission is required.", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = focusRequester) {
        if (showKeyboard && noteId == 0) {
            delay(300)
            focusRequester.requestFocus()
//            awaitFrame()
//            keyboard?.show()
        }
    }

    IntroShowcase(
        showIntroShowCase = !viewModel.isShowIntroNoteCompleted,
        onShowCaseCompleted = viewModel::onIntroNoteCompleted,
        dismissOnClickOutside = true,
        state = rememberIntroShowcaseState()
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
            topBar = {
                NoteDetailTopAppBar(
                    onNavigateUp = {
                        viewModel.saveNote(context)
                        onNavigateUp()
                    },
                    title = state.title,
                    onTitleChange = viewModel::onTitleChange,
                    isQuestionsAvailable = !state.questionsWithAnswers.isNullOrEmpty(),
                    onNavigateToQuestions = { state.id?.let { onNavigateToQuestions(it) } },
                    isDoneEnabled = (state.title.isNotEmpty()
                            || state.content.annotatedString.text.isNotEmpty())
                            && state.isNoteModified(),
                    onDone = {
                        viewModel.saveNote(context)
                        keyboard?.hide()
                    }
                )
            },
        ) { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            ) {
                RichTextEditor(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .weight(1f),
                    state = state.content,
                    colors = RichTextEditorDefaults.richTextEditorColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        textColor = MaterialTheme.colorScheme.onSurface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge,
                )

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    BottomMenu(
                        onOpenEditorControl = {
                            isEditorControlsVisible = !isEditorControlsVisible
                        },
                        onScanFromCamera = {
                            val permission = Manifest.permission.CAMERA
                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    permission
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                // Permission is already granted, proceed to step 2
                                val tmpUri = getTempUri()
                                tempUri.value = tmpUri
                                tempUri.value?.let { takePhotoLauncher.launch(it) }
                            } else {
                                // Permission is not granted, request it
                                cameraPermissionLauncher.launch(permission)
                            }
                        },
                        onTextCorrector = { viewModel.onTextCorrector(context = context) },
                        onGenerateQuestion = {
                            keyboard?.hide(); viewModel.onGenerateQuestion(context = context)
                        }
                    )

                    if (isEditorControlsVisible) {
                        EditorControl(
                            onBoldClick = {
                                state.content.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                            },
                            onItalicClick = {
                                state.content.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
                            },
                            onUnderlineClick = {
                                state.content.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                            },
                            onTitleClick = {
                                state.content.toggleSpanStyle(SpanStyle(fontSize = titleSize))
                            },
                            onSubtitleClick = {
                                state.content.toggleSpanStyle(SpanStyle(fontSize = subtitleSize))
                            },
                            onTextColorClick = {
                                state.content.toggleSpanStyle(SpanStyle(color = Color.Yellow))
                            },
                            onStartAlignClick = {
                                state.content.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Start))
                            },
                            onEndAlignClick = {
                                state.content.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.End))
                            },
                            onCenterAlignClick = {
                                state.content.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Center))
                            },
                            onCloseEditorControl = { isEditorControlsVisible = false }
                        )
                    }
                }
            }
        }
    }

    if (state.isGeneratingQuestion) {
        val lottieAnimation by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(
                R.raw.generate_question_animation
            )
        )
        val isLottiePlaying by remember { mutableStateOf(true) }
        val lottieProgress by animateLottieCompositionAsState(
            composition = lottieAnimation,
            iterations = LottieConstants.IterateForever,
            isPlaying = isLottiePlaying
        )

        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray.copy(alpha = 0.3f))
                    .clickable(enabled = true) { }
                    .then(modifier),
                contentAlignment = Alignment.Center
            ) {
                LottieAnimation(
                    composition = lottieAnimation,
                    progress = { lottieProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.extraLarge + MaterialTheme.spacing.large)
                )
            }
        }
    }
}