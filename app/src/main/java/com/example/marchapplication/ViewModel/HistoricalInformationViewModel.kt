package com.example.marchapplication.ui.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.marchapplication.Data.AppDatabase
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

    fun loadCarData(folderName: String) {
        viewModelScope.launch {
            _avatarResId.value = photoDao.getAvatarForCar(folderName).firstOrNull()
            _textEN.value = photoDao.getTextENForCar(folderName).firstOrNull() ?: "No data available"
            _textJP.value = photoDao.getTextJPForCar(folderName).firstOrNull() ?: "データがありません"
        }
    }

    fun speakText(text: String) {
        speakerUtils.speak(text)
    }

    fun stopReading() {
        speakerUtils.stop()
    }
}