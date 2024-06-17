package com.harissabil.notetion.ui.screen.home.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.canopas.lib.showcase.IntroShowcaseScope
import com.canopas.lib.showcase.component.ShowcaseStyle
import com.harissabil.notetion.ui.components.IntroShowCaseContent

@Composable
fun IntroShowcaseScope.AddFab(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = Modifier
            .introShowCaseTarget(
                index = 0,
                style = ShowcaseStyle.Default.copy(
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    backgroundAlpha = 0.98f,
                    targetCircleColor = MaterialTheme.colorScheme.inverseOnSurface
                )
            ) {
                IntroShowCaseContent(
                    title = "Add Notes",
                    description = "Easily add new notes with AI features designed to enhance your learning experience.",
                )
            }
            .then(modifier),
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = CircleShape,
        content = {
            Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Add notes")
        },
    )
}