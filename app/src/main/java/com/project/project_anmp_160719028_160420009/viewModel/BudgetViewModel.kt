package com.project.project_anmp_160719028_160420009.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.project.project_anmp_160719028_160420009.buildDb
import com.project.project_anmp_160719028_160420009.entity.BudgetEntity
import kotlinx.coroutines.launch

class BudgetViewModel(application: Application) : AndroidViewModel(application) {
    private val db = buildDb(application)
    private val budgetDao = db.budgetDao()

    val budgets = budgetDao.getAll()

    fun insert(budget: BudgetEntity, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                budgetDao.insert(budget)
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun update(budget: BudgetEntity, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                budgetDao.update(budget)
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun delete(budget: BudgetEntity, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                budgetDao.delete(budget)
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun getBudgetById(id: Int, onResult: (BudgetEntity?) -> Unit) {
        viewModelScope.launch {
            val result = budgetDao.getById(id)
            onResult(result)
        }
    }

    fun getTotalExpenseForBudget(budgetId: Int, onResult: (Float) -> Unit) {
        viewModelScope.launch {
            val result = budgetDao.getTotalExpenseForBudget(budgetId) ?: 0f
            onResult(result)
        }
    }
}
