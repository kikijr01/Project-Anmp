package com.project.project_anmp_160719028_160420009.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val username: String,
    val firstName: String,
    val lastName: String,
    var password: String
)
