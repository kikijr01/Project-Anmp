package com.project.project_anmp_160719028_160420009.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val username: String,
    val firstName: String,
    val lastName: String,
    val password: String
)
