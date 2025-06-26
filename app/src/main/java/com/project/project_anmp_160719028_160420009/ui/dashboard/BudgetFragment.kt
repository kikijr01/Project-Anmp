package com.project.project_anmp_160719028_160420009.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.project_anmp_160719028_160420009.BudgetAdapter
import com.project.project_anmp_160719028_160420009.R
import com.project.project_anmp_160719028_160420009.databinding.FragmentBudgetBinding
import com.project.project_anmp_160719028_160420009.viewModel.BudgetViewModel


class BudgetFragment : Fragment() {

    private var _binding: FragmentBudgetBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: BudgetAdapter
    private lateinit var viewModel: BudgetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[BudgetViewModel::class.java]

         adapter = BudgetAdapter(listOf()) { budget ->

             val action = BudgetFragmentDirections.actionNavigateBudgetingToEditBudget(budget.id!!)
             findNavController().navigate(action)

         }

        binding.recyclerViewBudget.layoutManager=LinearLayoutManager(requireContext());



        binding.recyclerViewBudget.adapter = adapter

        viewModel.budgets.observe(viewLifecycleOwner) { list ->
            Log.d("Budget",list.toString())
            adapter.updateData(list)
        }

        binding.fabAddBudget.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_budgeting_to_Add_Budget)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
