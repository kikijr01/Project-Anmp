package com.project.project_anmp_160719028_160420009.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.project.project_anmp_160719028_160420009.databinding.FragmentEditBudgetBinding
import com.project.project_anmp_160719028_160420009.model.BudgetEntity
import com.project.project_anmp_160719028_160420009.viewModel.BudgetViewModel

class EditBudgetFragment : Fragment() {
    private var _binding: FragmentEditBudgetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BudgetViewModel by viewModels()
    private val args: EditBudgetFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val budgetId = args.id

        viewModel.getBudgetById(budgetId) { budget ->
            if (budget != null) {
                binding.etEditBudgetName.setText(budget.name)
                binding.etEditBudgetNominal.setText(String.format("%.0f", budget.maxAmount))

                binding.btnUpdateBudget.setOnClickListener {
                    val newName = binding.etEditBudgetName.text.toString().trim()
                    val newNominalStr = binding.etEditBudgetNominal.text.toString().trim()

                    if (newName.isEmpty()) {
                        Toast.makeText(requireContext(), "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    val newNominal = newNominalStr.toFloatOrNull()
                    if (newNominal == null || newNominal < 0) {
                        Toast.makeText(requireContext(), "Nominal harus valid", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }


                    viewModel.getTotalExpenseForBudget(budgetId) { totalExpense ->
                        if (newNominal < totalExpense) {
                            Toast.makeText(
                                requireContext(),
                                "Budget tidak boleh lebih kecil dari total expense",
                                Toast.LENGTH_LONG
                            ).show()
                            return@getTotalExpenseForBudget
                        }

                        val updatedBudget = BudgetEntity(
                            id = budget.id,
                            name = newName,
                            maxAmount = newNominal
                        )
                        viewModel.update(updatedBudget) { success ->
                            if (success) {
                                Toast.makeText(requireContext(), "Budget berhasil diperbarui", Toast.LENGTH_SHORT).show()
                                findNavController().popBackStack()
                            } else {
                                Toast.makeText(requireContext(), "Gagal memperbarui budget", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Data budget tidak ditemukan", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
