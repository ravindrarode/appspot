package com.appspot.mvvmarchitecture.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appspot.mvvmarchitecture.data.api.ApiHelper
import com.appspot.mvvmarchitecture.data.repository.LoginActivityMainRepository
import com.appspot.mvvmarchitecture.ui.login.viewmodel.LoginActivityViewModel

class LoginActivityModelFactory(private val apiHelper: ApiHelper): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginActivityViewModel::class.java)) {
            return LoginActivityViewModel(LoginActivityMainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}