package com.example.marchapplication.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.marchapplication.Data.AppDatabase
import com.example.marchapplication.Data.Photo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class CarViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application.applicationContext)
    private val photoDao = database.photoDao()
    private val _photoData = MutableStateFlow<Photo?>(null)

    // Information of the car
    private val _carName = MutableStateFlow("")
    val carName: StateFlow<String> = _carName
    private val _dateCaptured = MutableStateFlow("")
    val dateCaptured: StateFlow<String> = _dateCaptured
    private val _location = MutableStateFlow("")
    val location: StateFlow<String> = _location
    private val _capturedBy = MutableStateFlow("")
    val capturedBy: StateFlow<String> = _capturedBy

    // Read Image
    fun loadPhotoData(imagePath: String) {
        viewModelScope.launch {
            val photo = withContext(Dispatchers.IO) {
                photoDao.getPhotoByPath(imagePath)
            }
            _photoData.value = photo
            photo?.let {
                _carName.value = it.CarName
                _dateCaptured.value = it.DateCaptured
                _location.value = it.Location
                _capturedBy.value = it.CapturedBy
            }
        }
    }
    // Write Image
    fun updatePhotoData() {
        viewModelScope.launch(Dispatchers.IO) {
            _photoData.value?.let {
                val updatedPhoto = it.copy(
                    CarName = _carName.value,
                    Location = _location.value,
                    CapturedBy = _capturedBy.value
                )
                photoDao.updatePhoto(updatedPhoto)
            }
        }
    }
    fun updateCarName(newName: String) {
        _carName.value = newName
    }

    fun updateLocation(newLocation: String) {
        _location.value = newLocation
    }

    fun updateCapturedBy(newCapturedBy: String) {
        _capturedBy.value = newCapturedBy
    }

    fun updateDateCaptured(newDate: String) {
        _dateCaptured.value = newDate
    }

    // List Car Image
    private val _imageList = MutableStateFlow<List<Photo>>(emptyList())
    val imageList: StateFlow<List<Photo>> = _imageList

    fun loadImagesByCar(folderName: String) {
        viewModelScope.launch {
            val images = withContext(Dispatchers.IO) {
                photoDao.getImagesByCar(folderName)
            }
            _imageList.value = images
        }
    }


}
