package com.harissabil.notetion.ui.screen.settings.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.harissabil.notetion.data.datastore.settings.Theme
import com.harissabil.notetion.ui.theme.spacing

@Composable
fun SettingsSection(
    modifier: Modifier = Modifier,
    themeValue: String,
    onThemeClick: (theme: Theme) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            var themeMenu by remember { mutableStateOf(false) }

            DropdownMenu(
                offset = DpOffset(88.dp, 0.dp),
                expanded = themeMenu,
                onDismissRequest = { themeMenu = false }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "System Default",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraSmall)
                        )
                    },
                    onClick = { themeMenu = false; onThemeClick(Theme.SYSTEM_DEFAULT) },
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Light",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraSmall)
                        )
                    },
                    onClick = { themeMenu = false; onThemeClick(Theme.LIGHT) },
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Dark",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraSmall)
                        )
                    },
                    onClick = { themeMenu = false; onThemeClick(Theme.DARK) },
                )
            }

            SettingItem(
                modifier = Modifier.animateContentSize(),
                icon = when (themeValue) {
                    "Dark" -> {
                        Icons.Outlined.DarkMode
                    }

                    "Light" -> {
                        Icons.Outlined.LightMode
                    }

                    else -> {
                        if (isSystemInDarkTheme()) {
                            Icons.Outlined.DarkMode
                        } else {
                            Icons.Outlined.LightMode
                        }
                    }
                },
                title = "Theme",
                value = themeValue,
                onCLick = { themeMenu = true }
            )
        }
    }
}