package com.example.marchapplication.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.Locale

class SpeakerUtils(context: Context) {
    private var textToSpeech: TextToSpeech? = null
    private var isPaused: Boolean = false

    init {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val currentLanguage = LocaleHelper.getSavedLanguage(context)
                val locale = if (currentLanguage == "ja") Locale.forLanguageTag("ja-JP") else Locale.US
                val result = textToSpeech?.setLanguage(locale)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("SpeakerUtils", "Language $locale not supported or missing data")
                } else {
                    Log.d("SpeakerUtils", "TTS set to $locale")
                }
            } else {
                Log.e("SpeakerUtils", "TTS initialization failed")
            }
        }
    }

    fun updateLanguage(language: String) {
        val locale = if (language == "ja") Locale.forLanguageTag("ja-JP") else Locale.US
        val result = textToSpeech?.setLanguage(locale)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Log.e("SpeakerUtils", "Language $locale not supported or missing data")
        } else {
            Log.d("SpeakerUtils", "TTS updated to $locale")
        }
    }

    fun speak(text: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "TTS_UTTERANCE")
        isPaused = false
    }

    fun stop() {
        textToSpeech?.stop()
        isPaused = false
    }

    fun setOnCompletionListener(listener: () -> Unit) {
        textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {}

            override fun onDone(utteranceId: String?) {
                listener()
            }

            override fun onError(utteranceId: String?) {}
        })
    }
}