package com.project.project_anmp_160719028_160420009.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.project.project_anmp_160719028_160420009.R
import com.project.project_anmp_160719028_160420009.databinding.FragmentBudgetBinding


class BudgetFragment : Fragment() {

    private var _binding: FragmentBudgetBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBudgetBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddBudget.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_budgeting_to_Add_Budget);
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}