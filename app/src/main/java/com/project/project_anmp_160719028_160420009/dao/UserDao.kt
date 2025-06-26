package com.project.project_anmp_160719028_160420009.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.project.project_anmp_160719028_160420009.entity.UserEntity

@Dao
interface UserDao {
    @Insert
    fun insert(user: UserEntity)

    @Query("SELECT * FROM user WHERE username = :username and password = :password  LIMIT 1")
    fun getUser(username: String, password:String): UserEntity?

    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    fun getUserByUsername(username: String): UserEntity?

    @Update
    fun updateUser(user: UserEntity)

}
