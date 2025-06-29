package com.project.project_anmp_160719028_160420009

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.project.project_anmp_160719028_160420009.databinding.FragmentAddExpenseBinding
import com.project.project_anmp_160719028_160420009.entity.ExpenseEntity
import com.project.project_anmp_160719028_160420009.viewModel.BudgetViewModel
import com.project.project_anmp_160719028_160420009.viewModel.ExpenseViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseFragment : Fragment() {

    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!

    private lateinit var budgetViewModel: BudgetViewModel
    private lateinit var expenseViewModel: ExpenseViewModel

    private var selectedBudgetId: Int? = null
    private var maxBudget: Float = 0f
    private var usedBudget: Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        budgetViewModel = ViewModelProvider(this)[BudgetViewModel::class.java]
        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        setupSpinner()
        setupDate()

        binding.btnAddExpense.setOnClickListener {
            addExpense()
        }
    }

    private fun setupDate() {
        val currentDate = SimpleDateFormat("dd MMM yyyy", Locale("in", "ID"))
            .format(Date(System.currentTimeMillis()))
        binding.tvDate.text = "Date: $currentDate"
    }

    private fun setupSpinner() {
        budgetViewModel.budgets.observe(viewLifecycleOwner) { budgetList ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                budgetList.map { it.name }
            )
            binding.spinnerBudget.adapter = adapter

            binding.spinnerBudget.setOnItemSelectedListener { _, _, position, _ ->
                val selectedBudget = budgetList[position]
                selectedBudgetId = selectedBudget.id

                budgetViewModel.getTotalBudgetById(selectedBudget.id) { max ->
                    maxBudget = max
                    budgetViewModel.getTotalExpenseForBudget(selectedBudget.id) { used ->
                        usedBudget = used
                        updateBudgetDisplay()
                    }
                }
            }
        }
    }

    private fun updateBudgetDisplay() {
        val formatRupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        binding.tvBudgetNow.text = formatRupiah.format(usedBudget)
        binding.tvBudgetMax.text = formatRupiah.format(maxBudget)

        val progress = if (maxBudget == 0f) 0 else ((usedBudget / maxBudget) * 100).toInt()
        binding.progressBudget.progress = progress
    }

    private fun addExpense() {
        val nominalStr = binding.etNominal.text.toString()
        val note = binding.etNote.text.toString()

        if (selectedBudgetId == null) {
            showToast("Please select a budget")
            return
        }

        if (nominalStr.isBlank()) {
            showToast("Nominal is required")
            return
        }

        val nominal = nominalStr.toFloatOrNull()
        if (nominal == null || nominal <= 0) {
            showToast("Nominal must be a positive number")
            return
        }

        if (nominal > maxBudget - usedBudget) {
            showToast("Nominal exceeds remaining budget")
            return
        }

        val expense = ExpenseEntity(
            nominal = nominal,
            note = note,
            timestamp = System.currentTimeMillis(),
            budgetId = selectedBudgetId!!
        )

        expenseViewModel.insert(expense) { success ->
            if (success) {
                showToast("Expense added successfully")
                requireActivity().onBackPressedDispatcher.onBackPressed()
            } else {
                showToast("Failed to add expense")
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun Spinner.setOnItemSelectedListener(
        listener: (parent: AdapterView<*>, view: View?, position: Int, id: Long) -> Unit
    ) {
        this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                listener(parent, view, position, id)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}