package com.project.project_anmp_160719028_160420009

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.project.project_anmp_160719028_160420009.databinding.FragmentAddBudgetBinding
import com.project.project_anmp_160719028_160420009.entity.BudgetEntity
import com.project.project_anmp_160719028_160420009.viewModel.BudgetViewModel

class AddBudgetFragment : Fragment() {
    private var _binding: FragmentAddBudgetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BudgetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddBudget.setOnClickListener {
            val name = binding.etBudgetName.text.toString().trim()
            val nominalStr = binding.etBudgetNominal.text.toString().trim()


            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "Nama budget tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (nominalStr.isEmpty()) {
                Toast.makeText(requireContext(), "Nominal tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nominal = nominalStr.toFloatOrNull()
            if (nominal == null || nominal < 0) {
                Toast.makeText(requireContext(), "Nominal harus berupa angka positif", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val budget = BudgetEntity(name = name, maxAmount = nominal)
            viewModel.insert(budget) {
                if (it) {
                    Toast.makeText(requireContext(), "Budget berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                } else {
                    Log.d("INSERT",it.toString())
                    Toast.makeText(requireContext(), "Gagal menambahkan budget", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
