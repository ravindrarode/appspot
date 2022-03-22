package com.appspot.mvvmarchitecture.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appspot.mvvmarchitecture.data.api.ApiHelper
import com.appspot.mvvmarchitecture.data.repository.DashboardActivityMainRepository
import com.appspot.mvvmarchitecture.ui.dashboard.viewmodel.DashboardViewModel

class DashboardActivityModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(DashboardActivityMainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}