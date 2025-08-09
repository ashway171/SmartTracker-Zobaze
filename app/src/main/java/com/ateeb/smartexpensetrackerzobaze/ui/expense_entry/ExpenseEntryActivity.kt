package com.ateeb.smartexpensetrackerzobaze.ui.expense_entry

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ateeb.smartexpensetrackerzobaze.MainApplication
import com.ateeb.smartexpensetrackerzobaze.databinding.ActivityExpenseEntryBinding
import com.ateeb.smartexpensetrackerzobaze.di.component.DaggerActivityComponent
import com.ateeb.smartexpensetrackerzobaze.di.module.ActivityModule
import com.ateeb.smartexpensetrackerzobaze.utils.permissions.PermissionManager
import com.ateeb.smartexpensetrackerzobaze.utils.permissions.PermissionType
import com.bumptech.glide.Glide

class ExpenseEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExpenseEntryBinding
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private val TAG = "ExpenseEntryActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        binding = ActivityExpenseEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCategorySpinner()

        // Manage Permissions
        initialisePermissionLauncher()
        initGalleryLauncher()

        binding.galleryCard.setOnClickListener {
            requestImageAccessPermission()
        }
    }

    private fun setupCategorySpinner() {
        val modes = listOf("Select Category", "Staff", "Travel", "Food", "Utility")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            modes
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.categorySpinner.adapter = adapter
        with(binding.categorySpinner) {
            setSelection(0)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedMode = parent.getItemAtPosition(position).toString()
                    // Handle the selected mode
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }


    private fun initialisePermissionLauncher() {
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
                PermissionManager.handlePermissionResult(
                    this,
                    result,
                    object : PermissionManager.PermissionCallback {
                        override fun onPermissionGranted() {
                            Log.d(TAG, "onPermissionGranted()")
                            // Handle permission-specific actions based on context
                            if (result.containsKey(Manifest.permission.READ_MEDIA_IMAGES) ||
                                result.containsKey(Manifest.permission.READ_EXTERNAL_STORAGE)
                            ) {
                                // Start image access-related functionality
                                launchGallery()
                            }
                        }

                        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
                        override fun onPermissionDenied() {
                            PermissionManager.showPermissionSettingsDialog(this@ExpenseEntryActivity)
                        }
                    })
            }
    }

    private fun initGalleryLauncher() {
        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    result.data?.data?.let { uri ->
                        Glide.with(this)
                            .load(uri)
                            .into(binding.campaignFaceIv)
                    } ?: run {
                        Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Gallery selection cancelled", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun requestImageAccessPermission() {
        PermissionManager.checkAndRequestPermissions(
            activity = this,
            permissionTypes = listOf(PermissionType.Image),
            launcher = permissionLauncher,
            callback = object : PermissionManager.PermissionCallback {
                override fun onPermissionGranted() {
                    launchGallery()
                }

                override fun onPermissionDenied() {
                    Toast.makeText(
                        this@ExpenseEntryActivity,
                        "Image Permission Denied",
                        Toast.LENGTH_SHORT
                    ).show();
                    PermissionManager.showPermissionSettingsDialog(this@ExpenseEntryActivity)
                }
            }
        )
    }

    private fun launchGallery() {
        try {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
            }

            // Check if a gallery app is available
            if (intent.resolveActivity(packageManager) == null) {
                Toast.makeText(this, "No gallery app available", Toast.LENGTH_SHORT).show()
                return
            }

            galleryLauncher.launch(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Error launching gallery: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun injectDependencies() {
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as MainApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .injectExpenseEntryActivity(this)
    }

}