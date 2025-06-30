package com.project.project_anmp_160719028_160420009.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.project_anmp_160719028_160420009.buildDb
import com.project.project_anmp_160719028_160420009.model.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val db = buildDb(getApplication())
    val user = MutableLiveData<UserEntity>()

    // For two-way binding
    val oldPassword = MutableLiveData<String>()
    val newPassword = MutableLiveData<String>()
    val repeatPassword = MutableLiveData<String>()

    // For listener binding
    var listener: ProfileActionListener? = null

    fun onSignOutClicked() {
        listener?.onSignOut()
    }

    fun onChangePasswordClicked() {
        val currentUser = user.value
        if (currentUser != null) {
            if (oldPassword.value != currentUser.password) {
                listener?.onShowMessage("Password lama tidak cocok")
                return
            }
            if (newPassword.value != repeatPassword.value) {
                listener?.onShowMessage("Repeat Password tidak sama")
                return
            }
            currentUser.password = newPassword.value ?: ""
            update(currentUser)
            listener?.onShowMessage("Password updated successfully")
        }
    }

    interface ProfileActionListener {
        fun onSignOut()
        fun onShowMessage(msg: String)
    }

    fun fetch(username: String) {
        viewModelScope.launch {
            val userResult = withContext(Dispatchers.IO) {
                db.userDao().getUserByUsername(username)
            }
            user.postValue(userResult!!)
        }
    }

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
    fun update(userEntity: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            db.userDao().updateUser(userEntity)
        }
    }

    fun login(username: String, password: String, onResult: (UserEntity?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = db.userDao().getUser(username, password)
            withContext(Dispatchers.Main) {
                onResult(user)
            }
        }
    }
    fun cekUsername(username: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                db.userDao().getUserByUsername(username)
            }
            onResult(user != null)
        }
    }

}