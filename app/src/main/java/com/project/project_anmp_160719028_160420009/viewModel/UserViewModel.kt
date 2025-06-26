package com.project.project_anmp_160719028_160420009.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.project.project_anmp_160719028_160420009.buildDb
import com.project.project_anmp_160719028_160420009.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val db = buildDb(getApplication())

    fun register(user: UserEntity, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    db.userDao().insert(user)
                }
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun login(username: String, password: String, onResult: (UserEntity?) -> Unit) {
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                db.userDao().getUser(username, password)
            }
            onResult(user)
        }
    }
}