package com.ateeb.smartexpensetrackerzobaze.ui.expense_entry

import android.Manifest
import android.content.Intent
import android.net.Uri
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
import androidx.lifecycle.lifecycleScope
import com.ateeb.smartexpensetrackerzobaze.MainApplication
import com.ateeb.smartexpensetrackerzobaze.R
import com.ateeb.smartexpensetrackerzobaze.databinding.ActivityExpenseEntryBinding
import com.ateeb.smartexpensetrackerzobaze.di.component.DaggerActivityComponent
import com.ateeb.smartexpensetrackerzobaze.di.module.ActivityModule
import com.ateeb.smartexpensetrackerzobaze.domain.model.Expense
import com.ateeb.smartexpensetrackerzobaze.ui.base.UiState
import com.ateeb.smartexpensetrackerzobaze.ui.expense_list.ExpenseListViewModel
import com.ateeb.smartexpensetrackerzobaze.utils.CategoryConstants
import com.ateeb.smartexpensetrackerzobaze.utils.ExpenseUtils
import com.ateeb.smartexpensetrackerzobaze.utils.permissions.PermissionManager
import com.ateeb.smartexpensetrackerzobaze.utils.permissions.PermissionType
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

class ExpenseEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExpenseEntryBinding

    // Since imageUri field can be nullable in db (Optional field)
    private var photoUri: Uri? = null
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    // Tag for logging; kept static to avoid issues with R8 obfuscation
    private val TAG = "ExpenseEntryActivity"

    @Inject
    lateinit var saveExpenseViewModel: SaveExpenseViewModel
    @Inject
    lateinit var expenseListViewModel: ExpenseListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Setup dependency injection for this activity
        injectDependencies()
        binding = ActivityExpenseEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Show total amount spent today
        binding.tvTotalSpentAmount.text = ExpenseUtils.TOTAL_AMOUNT_TODAY.toString()

        setupCategorySpinner()

        // Initialize permission and gallery launchers to handle runtime permissions and image selection
        initialisePermissionLauncher()
        initGalleryLauncher()

        // Open gallery when gallery card is clicked
        binding.galleryCard.setOnClickListener {
            requestImageAccessPermission()
        }

        submitButtonClick()
        setupObservers()
    }

    private fun setupCategorySpinner() {
        // Can be dealt via a viewmodel for further extension
        // Use predefined list of categories from CategoryConstants object for now
        val spinnerItems = listOf("Select Category") + CategoryConstants.CATEGORY_LIST
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            spinnerItems
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
                    val selectedCategory = spinnerItems[position]
                    // Handle the selected category
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }

    private fun initialisePermissionLauncher() {
        // Register for multiple permission results, delegate handling to PermissionManager
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
                PermissionManager.handlePermissionResult(
                    this,
                    result,
                    object : PermissionManager.PermissionCallback {
                        override fun onPermissionGranted() {
                            // If image permissions granted, launch gallery picker
                            if (result.containsKey(Manifest.permission.READ_MEDIA_IMAGES) ||
                                result.containsKey(Manifest.permission.READ_EXTERNAL_STORAGE)
                            ) {
                                Log.d(TAG, "onPermissionGranted()")
                                launchGallery()
                            }
                        }

                        // Show dialog directing user to app settings for manual permission granting
                        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
                        override fun onPermissionDenied() {
                            PermissionManager.showPermissionSettingsDialog(this@ExpenseEntryActivity)
                        }
                    })
            }
    }

    private fun initGalleryLauncher() {
        // Prepare launcher to receive result from gallery picking intent
        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    result.data?.data?.let { uri ->
                        // Store selected image URI and display preview
                        photoUri = uri
                        Glide.with(this)
                            .load(uri)
                            .into(binding.expenseReceiptIv)
                    } ?: run {
                        Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Gallery selection cancelled", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun requestImageAccessPermission() {
        // Check/request image access permission, then launch gallery on success
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

            // Check if any app can handle image intent
            if (intent.resolveActivity(packageManager) == null) {
                Toast.makeText(this, "No gallery app available", Toast.LENGTH_SHORT).show()
                return
            }

            galleryLauncher.launch(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Error launching gallery: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun validateInputs(): Boolean {
        // Validate all required fields, showing errors or toasts as necessary
        val title = binding.titleEt.text.toString().trim()
        if (title.isEmpty()) {
            binding.titleEt.error = getString(R.string.title_required)
            binding.titleEt.requestFocus()
            return false
        }
        val amountStr = binding.amountEt.text.toString().trim()
        if (amountStr.isEmpty()) {
            binding.amountEt.error = getString(R.string.amount_required)
            binding.amountEt.requestFocus()
            return false
        }
        val amount = amountStr.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            binding.amountEt.error = getString(R.string.amount_validate_ask)
            binding.amountEt.requestFocus()
            return false
        }
        val category = binding.categorySpinner.selectedItem?.toString() ?: ""
        if (category == "Select Category" || category.isEmpty()) {
            Toast.makeText(this, getString(R.string.category_select_ask), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun resetForm() {
        // Clear all input fields and reset spinner & image
        binding.titleEt.text?.clear()
        binding.amountEt.text?.clear()
        binding.notesEt.text?.clear()
        binding.categorySpinner.setSelection(0)
        photoUri = null
        // Clear the ImageView
        binding.expenseReceiptIv.setImageDrawable(null)
        //reset focus to title for convenience
        binding.titleEt.requestFocus()
    }

    private fun submitButtonClick() {
        binding.submitBtn.setOnClickListener {
            if (!validateInputs()) return@setOnClickListener

            val expense = Expense(
                title = binding.titleEt.text.toString().trim(),
                amount = binding.amountEt.text.toString().trim().toDouble(),
                notes = binding.notesEt.text.toString().trim().takeIf { it.isNotEmpty() },
                category = binding.categorySpinner.selectedItem.toString(),
                imageUri = photoUri?.toString(),
                date = Date()
            )
            Log.d(TAG, photoUri.toString())

            // Trigger save operation in ViewModel
            saveExpenseViewModel.saveExpense(expense)
        }
    }

    private fun setupObservers() {
        // Observe save operation state and update UI accordingly
        lifecycleScope.launch {
            saveExpenseViewModel.saveExpenseState.collect { state ->
                when (state) {
                    is UiState.Loading -> {
                        binding.progressBarParent.progressBar.visibility = View.VISIBLE
                        binding.submitBtn.isEnabled = false
                    }

                    is UiState.Success -> {
                        binding.progressBarParent.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this@ExpenseEntryActivity,
                            "Expense saved!",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Parse amount from input
                        val amountStr = binding.amountEt.text.toString().trim()
                        val amount = amountStr.toDoubleOrNull() ?: 0.0

                        // Update total amount today
                        ExpenseUtils.TOTAL_AMOUNT_TODAY += amount

                        // Update the TextView showing today's total amount
                        binding.tvTotalSpentAmount.text = "₹${"%.2f".format(ExpenseUtils.TOTAL_AMOUNT_TODAY)}"

                        resetForm() // to facilitate a new entry
                        binding.submitBtn.isEnabled = true
                    }

                    is UiState.Error -> {
                        binding.progressBarParent.progressBar.visibility = View.GONE
                        binding.submitBtn.isEnabled = true
                        Log.d(TAG, "Error: ${state.message}")
                        Toast.makeText(
                            this@ExpenseEntryActivity,
                            "Some error occurred while saving",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is UiState.Idle -> {
                        binding.progressBarParent.progressBar.visibility = View.GONE
                        binding.submitBtn.isEnabled = true
                    }

                    null -> TODO()
                }
            }
        }
    }

    private fun injectDependencies() {
        // Dagger injection setup for this activity
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as MainApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .injectExpenseEntryActivity(this)
    }

}