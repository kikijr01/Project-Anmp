package com.project.project_anmp_160719028_160420009

import android.content.Context

val DB_NAME = "expenseTrackerdb"
fun buildDb(context: Context): AppDatabase {
    val db = AppDatabase.buildDatabase(context)
    return db
}
