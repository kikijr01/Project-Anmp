package com.project.project_anmp_160719028_160420009

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.project_anmp_160719028_160420009.databinding.ItemBudgetBinding
import com.project.project_anmp_160719028_160420009.model.BudgetEntity
import java.text.NumberFormat
import java.util.Locale

class BudgetAdapter (
    private var list: List<BudgetEntity>,
    private val onEditClick: (BudgetEntity) -> Unit
) : RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {


    inner class BudgetViewHolder(private val binding: ItemBudgetBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(budget: BudgetEntity) {
            val formatRupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            binding.tvBudgetName.text=budget.name;
            binding.tvBudgetAmount.text=formatRupiah.format(budget.maxAmount)

            binding.root.setOnClickListener {
                onEditClick(budget)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val binding = ItemBudgetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BudgetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<BudgetEntity>) {
        list = newList
        notifyDataSetChanged()
    }


}
