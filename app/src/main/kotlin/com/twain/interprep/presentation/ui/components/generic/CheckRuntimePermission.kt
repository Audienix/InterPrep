package com.twain.interprep.presentation.ui.components.generic

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun CheckRuntimePermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    permissionString: String
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted, perform the desired action
            onPermissionGranted()
        } else {
            // Permission is denied, handle it accordingly
            onPermissionDenied()
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is already granted, perform the desired action
            onPermissionGranted()
        } else {
            // Permission is not granted, request the desired permission from the user
            permissionLauncher.launch(permissionString)
        }
    }
}
