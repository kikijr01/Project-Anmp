package com.project.project_anmp_160719028_160420009

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.project_anmp_160719028_160420009.dao.BudgetDao
import com.project.project_anmp_160719028_160420009.dao.ExpenseDao
import com.project.project_anmp_160719028_160420009.dao.UserDao
import com.project.project_anmp_160719028_160420009.entity.BudgetEntity
import com.project.project_anmp_160719028_160420009.entity.ExpenseEntity
import com.project.project_anmp_160719028_160420009.entity.UserEntity

@Database(
    entities = [UserEntity::class, BudgetEntity::class, ExpenseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun budgetDao(): BudgetDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun buildDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
