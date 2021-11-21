package com.deny.studyroom.ViewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deny.studyroom.data.User
import com.deny.studyroom.data.UserDao
import com.deny.studyroom.data.UserDatabase
import com.deny.studyroom.data.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application){

     val readAllData: LiveData<List<User>>
     val repository: UserRepository

    init {
        var userDao = UserDatabase.getDataBase(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(user)
        }
    }

    fun updateUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateUser(user)
        }
    }

    fun deleteUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteUser(user)
        }
    }

    fun deleteAllUser(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllUsers()
        }
    }
}