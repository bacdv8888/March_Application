package com.example.marchapplication.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

object LocaleHelper {
    private const val PREFS_NAME = "Settings"
    private const val LANGUAGE_KEY = "App_Lang"

    // Flow để phát ra ngôn ngữ hiện tại, mặc định là "en"
    private val _currentLanguageFlow = MutableStateFlow("en")
    val currentLanguageFlow: StateFlow<String> = _currentLanguageFlow

    // Lưu trạng thái ngôn ngữ vào SharedPreferences và cập nhật flow
    fun saveLanguagePreference(context: Context, language: String) {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(LANGUAGE_KEY, language)
            apply()
        }
        // Cập nhật giá trị cho flow
        _currentLanguageFlow.value = language
    }

    // Lấy trạng thái ngôn ngữ từ SharedPreferences
    fun getSavedLanguage(context: Context): String {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(LANGUAGE_KEY, "en") ?: "en"
    }

    // Đặt locale mới và cập nhật ngôn ngữ
    fun setLocale(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        // Lưu ngôn ngữ mới (và cập nhật flow thông qua hàm saveLanguagePreference)
        saveLanguagePreference(context, language)

        // Trả về context mới có cấu hình cập nhật
        return context.createConfigurationContext(config)
    }

    // Restart Activity để áp dụng ngôn ngữ mới
    fun restartActivity(activity: Activity) {
        val intent = activity.intent
        activity.finish()
        activity.startActivity(intent)
    }
}
