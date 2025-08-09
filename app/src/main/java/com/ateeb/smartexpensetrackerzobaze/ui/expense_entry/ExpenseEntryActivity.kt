package com.ateeb.smartexpensetrackerzobaze.ui.expense_entry

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ateeb.smartexpensetrackerzobaze.R
import com.ateeb.smartexpensetrackerzobaze.databinding.ActivityExpenseEntryBinding

class ExpenseEntryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityExpenseEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenseEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}