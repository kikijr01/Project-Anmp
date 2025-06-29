package com.project.project_anmp_160719028_160420009

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.project_anmp_160719028_160420009.databinding.FragmentExpenseBinding
import com.project.project_anmp_160719028_160420009.viewModel.ExpenseViewModel


class ExpenseFragment : Fragment() {

    private var _binding: FragmentExpenseBinding? = null
    private val binding get() = _binding!!

    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var expenseAdapter: ExpenseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentExpenseBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]


        expenseAdapter = ExpenseAdapter(listOf(), expenseViewModel,requireContext())


        binding.rvExpenses.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = expenseAdapter
        }


        expenseViewModel.allExpenses.observe(viewLifecycleOwner) { expenses ->
            expenseAdapter.updateData(expenses)
        }

        // FAB
        binding.fabAddExpense.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_track_to_addExpenseFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}