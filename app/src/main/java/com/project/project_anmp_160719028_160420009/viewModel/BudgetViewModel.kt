package com.project.project_anmp_160719028_160420009.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.project.project_anmp_160719028_160420009.buildDb
import com.project.project_anmp_160719028_160420009.model.BudgetEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BudgetViewModel(application: Application) : AndroidViewModel(application) {
    private val db = buildDb(application)
    private val budgetDao = db.budgetDao()


    val budgets: LiveData<List<BudgetEntity>> = budgetDao.getAll()

    fun insert(budget: BudgetEntity, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                // Pindahkan seluruh operasi DB ke IO dispatcher
                withContext(Dispatchers.IO) {
                    db.budgetDao().insert(budget)
                }
                onResult(true)
            } catch (e: Exception) {
                Log.e("BudgetViewModel", "Insert failed: ${e.message}", e)
                onResult(false)
            }
        }
    }


    fun update(budget: BudgetEntity, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    budgetDao.update(budget)
                }
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun delete(budget: BudgetEntity, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    budgetDao.delete(budget)
                }
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun getBudgetById(id: Int, onResult: (BudgetEntity?) -> Unit) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                budgetDao.getById(id)
            }
            onResult(result)
        }
    }

    fun getTotalExpenseForBudget(budgetId: Int, onResult: (Float) -> Unit) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                budgetDao.getTotalExpenseForBudget(budgetId) ?: 0f
            }
            onResult(result)
        }
    }

    fun getTotalBudgetById(budgetId: Int, onResult: (Float) -> Unit) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                budgetDao.getTotaBudgetById(budgetId) ?: 0f
            }
            onResult(result)
        }
    }

}
