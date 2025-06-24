package com.project.project_anmp_160719028_160420009.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.project.project_anmp_160719028_160420009.entity.ExpenseEntity

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insert(expense: ExpenseEntity)

    @Query("SELECT * FROM expense ORDER BY timestamp DESC")
    fun getAll(): LiveData<List<ExpenseEntity>>

    @Query("SELECT * FROM expense WHERE budgetId = :budgetId")
    suspend fun getByBudgetId(budgetId: Int): List<ExpenseEntity>
}
