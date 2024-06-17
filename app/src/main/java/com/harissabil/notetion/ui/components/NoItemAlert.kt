package com.harissabil.notetion.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.harissabil.notetion.ui.theme.spacing

@Composable
fun NoItemAlert(modifier: Modifier = Modifier, text: String) {

    val kaomojiList by rememberSaveable {
        mutableStateOf(
            listOf(
                "(￣ ￣|||)",
                "(；￣Д￣)",
                "(￣□￣」)",
                "(＃￣0￣)",
                "(＃￣ω￣)",
                "(￢_￢;)",
                "(」°ロ°)」",
                "(￣ヘ￣)",
                "(；一_一)",
                "(ノ_<。)",
                "o(TヘTo)",
                "( ; ω ; )",
                "(｡╯︵╰｡)",
                "(ಥ﹏ಥ)",
                "o(TヘTo)",
                "＼(º □ º l|l)/",
                "ヽ(￣～￣　)ノ",
                "┐(￣∀￣)┌",
                "┐(￣ヮ￣)┌",
                "(￣ω￣;)",
                "┐(￣ヘ￣;)┌"
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            Text(
                text = kaomojiList.random(),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 40.sp),
                maxLines = 1,
                textAlign = TextAlign.Center
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
        }
    }
}