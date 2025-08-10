package com.ateeb.smartexpensetrackerzobaze.ui.expense_list

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ateeb.smartexpensetrackerzobaze.MainApplication
import com.ateeb.smartexpensetrackerzobaze.R
import com.ateeb.smartexpensetrackerzobaze.databinding.FragmentExpenseDetailsBinding
import com.ateeb.smartexpensetrackerzobaze.di.component.DaggerFragmentComponent
import com.ateeb.smartexpensetrackerzobaze.di.module.FragmentModule
import com.ateeb.smartexpensetrackerzobaze.ui.base.UiState
import com.ateeb.smartexpensetrackerzobaze.utils.CategoryConstants
import com.ateeb.smartexpensetrackerzobaze.utils.DateUtils
import com.ateeb.smartexpensetrackerzobaze.utils.ExpenseUtils
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ExpenseDetailsFragment : Fragment() {

    private var _binding: FragmentExpenseDetailsBinding? = null
    private val binding get() = _binding!!

    private var selectedDate: Date = Calendar.getInstance().time  // initialize with today

    private lateinit var categoriesContainer: LinearLayout
    private var selectedCategoryView: TextView? = null

    private lateinit var adapter: ExpenseDetailsRVAdapter

    @Inject
    lateinit var expenseListViewModel: ExpenseListViewModel

    private var displayCategories = listOf("All") + CategoryConstants.CATEGORY_LIST
    private var lastSelectedCategory: String? = null

    private val TAG = "ExpenseDetailsFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize categories container
        categoriesContainer = binding.categoriesContainer

        displayCategories(displayCategories)
        highlightCategory(expenseListViewModel.selectedCategory)
        setupDatePicker()
        setupRecyclerView()
        setupExpenseListObserver()
        setupSummaryObserver()
        binding.fabAddExpense.visibility = View.VISIBLE
        binding.fabAddExpense.setOnClickListener { launchExpenseEntryActivity() }
    }

    private fun setupRecyclerView() {
        // Set the click listener on the injected adapter
        adapter = ExpenseDetailsRVAdapter { position, uri ->
            openInGallery(Uri.parse(uri))
        }

        // Setup RecyclerView
        binding.expensesRecyclerView.apply {
            adapter = this@ExpenseDetailsFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupDatePicker() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        // Set current date as default text
        binding.currentDateText.text = dateFormat.format(calendar.time)

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.time              // <-- update selectedDate here
            binding.currentDateText.text = dateFormat.format(selectedDate)

            // Select "All" category when date changes
            val allCategoryView = categoriesContainer.children
                .filterIsInstance<TextView>()
                .firstOrNull { it.text.toString() == "All" }

            allCategoryView?.let {
                onCategorySelected(it, "All")
            }

            val (startDate, endDate) = DateUtils.getStartAndEndDatesFor(selectedDate)
            expenseListViewModel.loadExpensesByDate(startDate, endDate)

        }

        val openDatePicker = {
            DatePickerDialog(
                requireContext(),  // or requireContext() if inside a Fragment
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.datePickerIcon.setOnClickListener { openDatePicker() }
        binding.datePickerRow.setOnClickListener { openDatePicker() }
    }


    private fun launchExpenseEntryActivity() {
        findNavController().navigate(R.id.expenseEntryActivity)
    }

    private fun displayCategories(categories: List<String>) {
        // Clear existing views
        categoriesContainer.removeAllViews()

        categories.forEach { category ->
            addCategoryView(category)
        }

        // Select "All" category view programmatically after adding all views
        val allCategoryView = categoriesContainer.children
            .filterIsInstance<TextView>()
            .firstOrNull { it.text.toString() == "All" }

        allCategoryView?.let {
            onCategorySelected(it, "All")
        }
    }

    private fun addCategoryView(categoryName: String) {
        val categoryView = layoutInflater.inflate(
            R.layout.category_item_layout,
            categoriesContainer,
            false
        ) as TextView

        categoryView.text = categoryName

        categoryView.setOnClickListener {
            onCategorySelected(categoryView, categoryName)
        }

        categoriesContainer.addView(categoryView)
    }

    private fun onCategorySelected(selectedView: TextView, categoryName: String) {
        if (lastSelectedCategory == categoryName) return

        selectedCategoryView?.isSelected = false
        selectedView.isSelected = true
        selectedCategoryView = selectedView
        lastSelectedCategory = categoryName

        // Update ViewModel with current selection
        expenseListViewModel.selectedCategory = categoryName

        val (start, end) = DateUtils.getStartAndEndDatesFor(selectedDate)
        if (categoryName == "All") expenseListViewModel.loadExpensesByDate(start, end)
        else expenseListViewModel.loadExpensesByDateAndCategory(start, end, categoryName)
    }

    private fun onCategoryClicked(category: String) {
        expenseListViewModel.selectedCategory = category
        highlightCategory(category)
    }

    private fun highlightCategory(category: String) {
        // Clear previous selection styles
        categoriesContainer.children.forEach { it.isSelected = false }

        // Find and highlight the matching view
        categoriesContainer.children
            .filterIsInstance<TextView>()
            .firstOrNull { it.text.toString() == category }
            ?.isSelected = true
    }

    private fun openInGallery(uri: Uri) {
        try {
            // Copy the image to cache
            val file = File(requireContext().cacheDir, "image_${System.currentTimeMillis()}.jpg")
            requireContext().contentResolver.openInputStream(uri)?.use { inputStream ->
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            } ?: throw Exception("Failed to access Uri")

            // Get FileProvider Uri
            val fileUri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.fileprovider",
                file
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(fileUri, "image/*")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Error opening image: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
            Log.e(TAG, "Error opening image: ${e.message}")
        }
    }

    private fun setupExpenseListObserver() {
        lifecycleScope.launch {
            expenseListViewModel.expenses.collect { state ->
                when (state) {
                    is UiState.Error -> {
                        binding.progressBarParent.progressBar.visibility = View.GONE
                        Log.e(TAG, state.message)
                        // Show empty state or error state
                        binding.emptyStateContainer.visibility = View.VISIBLE
                        binding.expensesRecyclerView.visibility = View.GONE
                        // Optionally update emptyStateText with error message
                        binding.emptyStateText.text = "Error loading expenses"
                    }

                    UiState.Idle -> {
                        binding.progressBarParent.progressBar.visibility = View.VISIBLE
                        binding.emptyStateContainer.visibility = View.VISIBLE
                        binding.expensesRecyclerView.visibility = View.GONE
                    }

                    UiState.Loading -> {
                        binding.progressBarParent.progressBar.visibility = View.VISIBLE
                        binding.emptyStateContainer.visibility = View.GONE
                        binding.expensesRecyclerView.visibility = View.GONE
                    }

                    is UiState.Success -> {
                        binding.progressBarParent.progressBar.visibility = View.GONE
                        Log.d(TAG, state.data.toString())
                        adapter.updateList(state.data)

                        if (state.data.isNullOrEmpty()) {
                            // Show empty state, hide list
                            binding.emptyStateContainer.visibility = View.VISIBLE
                            binding.expensesRecyclerView.visibility = View.GONE
                            binding.emptyStateText.text = "No Expenses Yet"
                        } else {
                            // Show list, hide empty state
                            binding.emptyStateContainer.visibility = View.GONE
                            binding.expensesRecyclerView.visibility = View.VISIBLE

                            // Submit your expenses to adapter here
                            adapter.updateList(state.data)

                            // Check if selectedDate is today, then show Toast
                            if (isSelectedDateToday()) {
                                ExpenseUtils.TOTAL_AMOUNT_TODAY = ExpenseUtils.sumExpenseAmounts(state.data)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupSummaryObserver() {
        lifecycleScope.launch {
            expenseListViewModel.expenseSummary.collect { state ->
                when (state) {
                    is UiState.Error -> {
                        // Hide summary UI or show error message
                    }

                    UiState.Idle -> {
                        // Optionally show placeholder/loading state
                    }

                    UiState.Loading -> {
                        // Show loading spinner for summary if needed
                    }

                    is UiState.Success -> {
                        // Display values
                        binding.totalItemsValue.text = state.data.totalItems.toString()
                        binding.totalAmountValue.text = "₹${state.data.totalAmount}"
                    }
                }
            }
        }
    }

    private fun isSelectedDateToday(): Boolean {
        val todayCal = Calendar.getInstance()  // current date/time
        val selectedCal = Calendar.getInstance().apply { time = selectedDate }

        return todayCal.get(Calendar.YEAR) == selectedCal.get(Calendar.YEAR) &&
                todayCal.get(Calendar.DAY_OF_YEAR) == selectedCal.get(Calendar.DAY_OF_YEAR)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun injectDependencies() {
        DaggerFragmentComponent.builder()
            .applicationComponent((requireActivity().application as MainApplication).applicationComponent)
            .fragmentModule(FragmentModule(this))
            .build()
            .inject(this)

    }

}