package com.harissabil.notetion.data.gemini

import com.google.ai.client.generativeai.GenerativeModel
import com.harissabil.notetion.BuildConfig

class GeminiClient {

    val geneminiProVisionModel by lazy {
        GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.GEMINI_API_KEY,
        )
    }
}