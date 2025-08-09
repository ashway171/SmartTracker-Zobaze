package com.ateeb.smartexpensetrackerzobaze.utils.permissions

import android.Manifest
import android.os.Build

sealed class PermissionType(val permissions: Array<String>, val rationale: String? = null) {

    data object Image : PermissionType(
        permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        },
        rationale = "Image access is needed to select photos from your gallery."
    )

}
