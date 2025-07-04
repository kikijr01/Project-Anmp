package com.project.project_anmp_160719028_160420009.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.project.project_anmp_160719028_160420009.model.BudgetEntity

@Dao
interface BudgetDao {
    @Insert
     fun insert(budget: BudgetEntity)

    @Update
    fun update(budget: BudgetEntity)

    @Query("SELECT * FROM budget")
    fun getAll(): LiveData<List<BudgetEntity>>

    @Query("SELECT * FROM budget WHERE id = :id")
    fun getById(id: Int): BudgetEntity?

    @Delete
    fun delete(budget: BudgetEntity)

    @Query("SELECT name FROM budget WHERE id = :budgetId")
    fun getBudgetNameById(budgetId: Int): String

    @Query("SELECT maxAmount FROM budget WHERE id = :budgetId")
    fun getTotaBudgetById(budgetId: Int): Float?


    @Query("SELECT SUM(nominal) FROM expense WHERE budgetId = :budgetId")
    fun getTotalExpenseForBudget(budgetId: Int): Float?
}
