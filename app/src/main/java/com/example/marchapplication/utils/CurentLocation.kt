package com.example.marchapplication.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import java.util.Locale

object LocationHelper {
    const val LOCATION_PERMISSION_REQUEST_CODE = 1001

    // Kiểm tra xem đã có quyền truy cập vị trí chưa
    fun hasLocationPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    // Yêu cầu quyền vị trí từ Activity
    fun requestLocationPermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    // Lấy vị trí hiện tại sử dụng Fused Location Provider, xử lý SecurityException nếu có
    fun fetchLocation(context: Context, onResult: (Location?) -> Unit) {
        if (!hasLocationPermission(context)) {
            onResult(null)
            return
        }
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    onResult(location)
                }
                .addOnFailureListener { e ->
                    onResult(null)
                }
        } catch (e: SecurityException) {
            onResult(null)
        }
    }
    // Chuyển đổi tọa đô thành tên thành phố
    fun getDetailedAddress(context: Context, latitude: Double, longitude: Double): String? {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1) ?: emptyList()
            if (addresses.isNotEmpty()) {
                val address = addresses[0]

                val street = address.thoroughfare ?: ""
                val subLocality = address.subLocality ?: ""
                val adminArea = address.adminArea ?: ""//
                val locality = address.locality ?: ""
                listOf(street, subLocality, locality, adminArea)
                    .filter { it.isNotBlank() }
                    .joinToString(", ")
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}
