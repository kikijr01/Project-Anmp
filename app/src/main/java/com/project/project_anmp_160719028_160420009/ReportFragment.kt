package com.project.project_anmp_160719028_160420009

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.project_anmp_160719028_160420009.databinding.FragmentReportBinding
import com.project.project_anmp_160719028_160420009.viewModel.BudgetViewModel
import java.text.NumberFormat
import java.util.*

class ReportFragment : Fragment() {

    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BudgetViewModel
    private lateinit var adapter: ReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[BudgetViewModel::class.java]
        adapter = ReportAdapter(listOf(), viewModel)

        binding.rvReport.layoutManager = LinearLayoutManager(requireContext())
        binding.rvReport.adapter = adapter

        viewModel.budgets.observe(viewLifecycleOwner) { budgetList ->
            adapter.updateData(budgetList)


            var totalBudget = 0f
            var totalExpense = 0f
            val formatRupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

            budgetList.forEach { budget ->
                viewModel.getTotalBudgetById(budget.id) { max ->
                    totalBudget += max

                    viewModel.getTotalExpenseForBudget(budget.id) { used ->
                        totalExpense += used


                        binding.tvTotal.text = "Total Expenses / Budget\n" +
                                "${formatRupiah.format(totalExpense)} / ${formatRupiah.format(totalBudget)}"
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}