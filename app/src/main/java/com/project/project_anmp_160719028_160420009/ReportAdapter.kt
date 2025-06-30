package com.project.project_anmp_160719028_160420009

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.project_anmp_160719028_160420009.databinding.ItemReportBinding
import com.project.project_anmp_160719028_160420009.model.BudgetEntity
import com.project.project_anmp_160719028_160420009.viewModel.BudgetViewModel

import java.text.NumberFormat
import java.util.Locale

class ReportAdapter (
    private var list: List<BudgetEntity>,
    private val viewModel: BudgetViewModel,
) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    inner class ReportViewHolder(private val binding: ItemReportBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(budget: BudgetEntity) {
            val formatRupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            binding.tvBudgetName.text = budget.name

            var totalBudget: Float? = null
            var totalExpense: Float? = null

            val updateUI= {
                if (totalBudget != null && totalExpense != null) {
                    val progress = if (totalBudget == 0f) 0 else ((totalExpense!! / totalBudget!!) * 100).toInt()
                    val sisa = totalBudget!! - totalExpense!!

                    binding.tvMax.text = formatRupiah.format(totalBudget)
                    binding.tvUsed.text = formatRupiah.format(totalExpense)
                    binding.tvBudgetLeft.text = "Budget left: ${formatRupiah.format(sisa)}"
                    binding.progressBudget.progress = progress
                }
            }

            viewModel.getTotalBudgetById(budget.id) { budgetValue ->
                totalBudget = budgetValue
                updateUI()
            }

            viewModel.getTotalExpenseForBudget(budget.id) { expenseValue ->
                totalExpense = expenseValue
                updateUI()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ItemReportBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<BudgetEntity>) {
        list = newList
        notifyDataSetChanged()
    }
}
