package com.avas.photosapp.helper

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat

/**
 * Created by Aabhash Shakya on 4/10/2024
 */

val MEDIA_IMAGES_READ_PERMISSION = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
    android.Manifest.permission.READ_MEDIA_IMAGES else android.Manifest.permission.READ_EXTERNAL_STORAGE

fun checkPermissionGranted(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        context, permission
    ) == PackageManager.PERMISSION_GRANTED

}

fun requestPermissionIfNeeded(
    context: Context, permission: String, launcher: ManagedActivityResultLauncher<String, Boolean>
) {
    if (!checkPermissionGranted(context, permission)) {
        launcher.launch(permission)
    }
}