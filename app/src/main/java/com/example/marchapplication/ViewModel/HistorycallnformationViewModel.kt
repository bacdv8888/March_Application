package com.example.marchapplication.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.marchapplication.Data.AppDatabase
import com.example.marchapplication.utils.LocaleHelper
import com.example.marchapplication.utils.SpeakerUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class HistoricalInformationViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private val speakerUtils = SpeakerUtils(context)
    private val database = AppDatabase.getDatabase(context)
    private val photoDao = database.photoDao()

    private val _avatarResId = MutableStateFlow<Int?>(null)
    val avatarResId: StateFlow<Int?> = _avatarResId

    private val _textEN = MutableStateFlow("Loading.....")
    val textEN: StateFlow<String> = _textEN

    private val _textJP = MutableStateFlow("読み込み中.....")
    val textJP: StateFlow<String> = _textJP

    private val _currentLanguage = MutableStateFlow("en")
    val currentLanguage: StateFlow<String> = _currentLanguage

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _isPaused = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused

    init {
        viewModelScope.launch {
            LocaleHelper.currentLanguageFlow.collect { language ->
                _currentLanguage.value = language
            }
        }

        speakerUtils.setOnCompletionListener {
            _isPlaying.value = false
            _isPaused.value = false
        }
    }



    fun loadCarData(folderName: String) {
        viewModelScope.launch {
            _avatarResId.value = photoDao.getAvatarForCar(folderName).firstOrNull()
            _textEN.value = photoDao.getTextENForCar(folderName).firstOrNull() ?: "No data available"
            _textJP.value = photoDao.getTextJPForCar(folderName).firstOrNull() ?: "データがありません"
        }
    }

    fun speakText() {
        val currentLang = _currentLanguage.value
        val textToSpeak = if (currentLang == "en") _textEN.value else _textJP.value
        speakerUtils.updateLanguage(currentLang)
        speakerUtils.speak(textToSpeak)

        _isPlaying.value = true
        _isPaused.value = false
    }

    fun stopReading() {
        speakerUtils.stop()
        _isPlaying.value = false
        _isPaused.value = false
    }
}