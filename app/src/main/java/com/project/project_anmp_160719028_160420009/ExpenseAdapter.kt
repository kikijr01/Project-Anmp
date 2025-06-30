package com.project.project_anmp_160719028_160420009

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.project_anmp_160719028_160420009.databinding.ItemDialogExpenseBinding
import com.project.project_anmp_160719028_160420009.databinding.ItemExpanseBinding
import com.project.project_anmp_160719028_160420009.model.ExpenseEntity
import com.project.project_anmp_160719028_160420009.viewModel.ExpenseViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class ExpenseAdapter(
    private var list: List<ExpenseEntity>,
    private val viewModel: ExpenseViewModel,
    private val context: Context
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    inner class ExpenseViewHolder(private val binding: ItemExpanseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(expense: ExpenseEntity) {
            val formatRupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            val date = SimpleDateFormat("dd MMM yyyy HH:mm", Locale("in", "ID"))
                .format(Date(expense.timestamp))

            binding.tvItemDate.text = date
            binding.tvItemNominal.text = formatRupiah.format(expense.nominal)

            viewModel.getBudgetNameById(expense.budgetId) { budgetName ->
                binding.tvItemBudgetChip.text = budgetName
            }


            binding.root.setOnClickListener {
                showDetailDialog(expense)
            }
        }

        private fun showDetailDialog(expense: ExpenseEntity) {
            val dialogBinding = ItemDialogExpenseBinding.inflate(
                LayoutInflater.from(context),
                null,
                false
            )

            val date = SimpleDateFormat("dd MMM yyyy HH:mm", Locale("in", "ID"))
                .format(Date(expense.timestamp))
            val formatRupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

            dialogBinding.tvDialogDate.text = date
            dialogBinding.tvDialogNominal.text = formatRupiah.format(expense.nominal)
            dialogBinding.tvDialogNote.text = expense.note

            viewModel.getBudgetNameById(expense.budgetId) { budgetName ->
                dialogBinding.tvDialogBudgetChip.text = budgetName

                val dialog = AlertDialog.Builder(context)
                    .setView(dialogBinding.root)
                    .create()

                dialogBinding.btnClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialog.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ItemExpanseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<ExpenseEntity>) {
        list = newList
        notifyDataSetChanged()
    }
}
