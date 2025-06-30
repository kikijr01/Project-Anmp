package com.project.project_anmp_160719028_160420009.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.project.project_anmp_160719028_160420009.model.ExpenseEntity

@Dao
interface ExpenseDao {
    @Insert
    fun insertExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expense ORDER BY timestamp DESC")
    fun getAllExpenses(): LiveData<List<ExpenseEntity>>

    @Query("""
        SELECT * FROM expense 
        WHERE budgetId = :budgetId 
        ORDER BY timestamp DESC
    """)
    fun getExpensesByBudget(budgetId: Int): LiveData<List<ExpenseEntity>>

    @Query("SELECT SUM(nominal) FROM expense WHERE budgetId = :budgetId")
    fun getTotalExpenseForBudget(budgetId: Int): Float?

    @Query("SELECT SUM(nominal) FROM expense")
    fun getTotalExpenses(): LiveData<Float?>
}
