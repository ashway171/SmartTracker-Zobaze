package com.ateeb.smartexpensetrackerzobaze.ui.expense_list

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ateeb.smartexpensetrackerzobaze.R
import com.ateeb.smartexpensetrackerzobaze.databinding.FragmentExpenseDetailsBinding
import com.ateeb.smartexpensetrackerzobaze.ui.expense_entry.ExpenseEntryActivity

class ExpenseDetailsFragment : Fragment() {

    private var _binding: FragmentExpenseDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        binding.fabAddExpense.visibility = View.VISIBLE
        binding.fabAddExpense.setOnClickListener { launchExpenseEntryActivity() }
    }

    private fun launchExpenseEntryActivity(){
        findNavController().navigate(R.id.expenseEntryActivity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}