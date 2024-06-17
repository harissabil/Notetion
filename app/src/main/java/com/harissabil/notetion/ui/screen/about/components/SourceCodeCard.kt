package com.harissabil.notetion.ui.screen.about.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.harissabil.notetion.R
import com.harissabil.notetion.ui.components.CardItem
import com.harissabil.notetion.ui.theme.spacing

@Composable
fun SourceCodeCard(
    modifier: Modifier = Modifier,
    link: String,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small + MaterialTheme.spacing.extraSmall),
    ) {
        Text(
            text = "Source Code",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.alpha(0.75f),
        )
        Card {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            CardItem(
                image = R.drawable.github_mark,
                imageSize = 24.dp,
                title = "GitHub",
                description = link,
                modifier = Modifier.clickable {
                    val intent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(link))
                    context.startActivity(intent)
                }
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        }
    }
}