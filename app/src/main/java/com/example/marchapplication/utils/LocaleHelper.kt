package com.yourapp.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LocaleHelper {
    private const val PREFS_NAME = "Settings"
    private const val LANGUAGE_KEY = "App_Lang"

    // Lưu trạng thái ngôn ngữ vào SharedPreferences
    fun saveLanguagePreference(context: Context, language: String) {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(LANGUAGE_KEY, language)
            apply()
        }
    }

    // Lấy trạng thái ngôn ngữ từ SharedPreferences
    fun getSavedLanguage(context: Context): String {
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(LANGUAGE_KEY, "en") ?: "en"
    }

    // Đặt locale mới và cập nhật ngôn ngữ
    fun setLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        // Lưu ngôn ngữ mới
        saveLanguagePreference(context, language)
    }

    // Restart Activity để áp dụng ngôn ngữ mới
    fun restartActivity(activity: Activity) {
        val intent = activity.intent
        activity.finish()
        activity.startActivity(intent)
    }
}
