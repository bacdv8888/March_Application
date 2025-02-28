package com.example.marchapplication.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.runtime.Composable
import java.io.File

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.activity.compose.ManagedActivityResultLauncher

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class CameraState(
    val hasCameraPermission: MutableState<Boolean>,
    val cameraPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    val cameraLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    val capturedImage: MutableState<Bitmap?>,
    val capturedImageUri: MutableState<Uri?>
)
@Composable
fun rememberCameraState(
    navController: NavController
): CameraState {
    val context = LocalContext.current

    val hasCameraPermission = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED
        )
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission.value = granted
        if (!granted) {
            Toast.makeText(context, "You need to grant Camera permission in Settings!", Toast.LENGTH_LONG).show()
            val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", context.packageName, null)
            }
            context.startActivity(intent)
        }
    }

    val capturedImage = remember { mutableStateOf<Bitmap?>(null) }
    val capturedImageUri = remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            capturedImageUri.value?.let { uri ->
                val imageBitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
                capturedImage.value = imageBitmap
                navController.navigate("modelSettingScreen/${Uri.encode(uri.toString())}")
            }
        }
    }

    return CameraState(
        hasCameraPermission = hasCameraPermission,
        cameraPermissionLauncher = cameraPermissionLauncher,
        cameraLauncher = cameraLauncher,
        capturedImage = capturedImage,
        capturedImageUri = capturedImageUri
    )
}

fun launchCamera(context: Context, cameraLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>, capturedImageUri: MutableState<Uri?>) {
    val photoFile = createImageFile(context)
    val photoURI: Uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", photoFile)
    capturedImageUri.value = photoURI

    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
        putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
    }
    cameraLauncher.launch(takePictureIntent)
}

fun createImageFile(context: Context): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
}


