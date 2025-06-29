package com.project.project_anmp_160719028_160420009.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.project.project_anmp_160719028_160420009.buildDb
import com.project.project_anmp_160719028_160420009.entity.ExpenseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val db = buildDb(application)
    private val expenseDao = db.expenseDao()

    val allExpenses: LiveData<List<ExpenseEntity>> = expenseDao.getAllExpenses()
    val totalExpenses: LiveData<Float?> = expenseDao.getTotalExpenses()

    fun insert(expense: ExpenseEntity, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    expenseDao.insertExpense(expense)
                }
                onResult(true)
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Insert failed: ${e.message}", e)
                onResult(false)
            }
        }
    }

    fun getExpensesByBudget(budgetId: Int): LiveData<List<ExpenseEntity>> {
        return expenseDao.getExpensesByBudget(budgetId)
    }
    fun getBudgetNameById(budgetId: Int, onResult: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val name = withContext(Dispatchers.IO) {
                    db.budgetDao().getBudgetNameById(budgetId)
                }
                onResult(name)
            } catch (e: Exception) {
                onResult("Unknown")
            }
        }
    }


    fun getTotalExpenseForBudget(budgetId: Int, onResult: (Float) -> Unit) {
        viewModelScope.launch {
            try {
                val total = withContext(Dispatchers.IO) {
                    expenseDao.getTotalExpenseForBudget(budgetId) ?: 0f
                }
                onResult(total)
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Error getting total expense: ${e.message}", e)
                onResult(0f)
            }
        }
    }
}