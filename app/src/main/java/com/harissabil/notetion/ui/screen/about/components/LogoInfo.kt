package com.harissabil.notetion.ui.screen.about.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.harissabil.notetion.ui.theme.spacing

@Composable
fun LogoInfo(
    modifier: Modifier = Modifier,
    appIcon: Drawable,
    appVersion: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        AsyncImage(
            model = appIcon,
            contentDescription = "App Icon",
            modifier = Modifier.size(98.dp)
        )
        Text(
            text = "Notetion",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = appVersion,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alpha(0.75f)
        )
    }
}