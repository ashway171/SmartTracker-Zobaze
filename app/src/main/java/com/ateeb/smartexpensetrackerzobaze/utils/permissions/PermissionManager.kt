package com.ateeb.smartexpensetrackerzobaze.utils.permissions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED

object PermissionManager {

    interface PermissionCallback {
        fun onPermissionGranted()
        fun onPermissionDenied()
    }

    fun checkAndRequestPermissions(
        activity: Activity,
        permissionTypes: List<PermissionType>,
        launcher: ActivityResultLauncher<Array<String>>,
        callback: PermissionCallback
    ) {
        // Flatten permissions from all PermissionTypes and filter un-granted ones
        val deniedPermissions = permissionTypes.flatMap { it.permissions.toList() }
            .filter { ContextCompat.checkSelfPermission(activity, it) != PERMISSION_GRANTED }
            .distinct()

        if (deniedPermissions.isEmpty()) {
            callback.onPermissionGranted()
            return
        }

        // Check if any permission requires a rationale
        val permissionTypeNeedingRationale = permissionTypes.find { permissionType ->
            permissionType.rationale != null && permissionType.permissions.any { permission ->
                activity.shouldShowRequestPermissionRationale(permission)
            }
        }

        if (permissionTypeNeedingRationale != null) {
            // Show rationale dialog for the permission type needing it
            showRationaleDialog(activity, permissionTypeNeedingRationale.rationale!!) {
                // Launch permission request after rationale is acknowledged
                launcher.launch(deniedPermissions.toTypedArray())
            } ?: callback.onPermissionDenied() // Call onPermissionDenied if rationale dialog is canceled
        } else {
            // Launch permission request directly if no rationale is needed
            launcher.launch(deniedPermissions.toTypedArray())
        }

    }

    fun handlePermissionResult(
        activity: Activity,
        permissions: Map<String, Boolean>,
        callback: PermissionCallback
    ) {
        val denied = permissions.filterValues { !it }
        if (denied.isEmpty()) {
            callback.onPermissionGranted()
            return
        } else {
            callback.onPermissionDenied()
        }
    }


    fun showPermissionSettingsDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Permission Required")
            .setMessage("Features on this screen need permissions to function properly. Please allow from app settings.")
            .setPositiveButton("Go to Settings") { _, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", context.packageName, null)
                )
                context.startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showRationaleDialog(context: Context, rationale: String, onProceed: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle("Permission Required")
            .setMessage(rationale)
            .setPositiveButton("OK") { _, _ -> onProceed() }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }

}