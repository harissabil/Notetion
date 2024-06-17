package com.harissabil.notetion.ui.screen.note_detail.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatAlignRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harissabil.notetion.ui.theme.NotetionTheme

@Composable
fun ControlWrapper(
    modifier: Modifier = Modifier,
    selected: Boolean,
    selectedContainerColor: Color = MaterialTheme.colorScheme.secondary,
    selectedContentColor: Color = MaterialTheme.colorScheme.onSecondary,
    unselectedContainerColor: Color = MaterialTheme.colorScheme.surface,
    unselectedContentColor: Color = MaterialTheme.colorScheme.onSurface,
    onChangeClick: (Boolean) -> Unit,
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(size = 6.dp))
            .clickable {
                onClick()
                onChangeClick(!selected)
            }
            .background(
                if (selected) selectedContainerColor
                else unselectedContainerColor
            )
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(size = 6.dp)
            )
            .padding(all = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (selected) selectedContentColor else unselectedContentColor
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ControlWrapperPreview() {
    NotetionTheme {
        Row {
            ControlWrapper(
                selected = true,
                onChangeClick = {},
                onClick = {},
                icon = Icons.AutoMirrored.Filled.FormatAlignRight,
                contentDescription = "End Align Control",
            )
            ControlWrapper(
                selected = false,
                onChangeClick = {},
                onClick = {},
                icon = Icons.AutoMirrored.Filled.FormatAlignRight,
                contentDescription = "End Align Control",
            )
        }
    }
}