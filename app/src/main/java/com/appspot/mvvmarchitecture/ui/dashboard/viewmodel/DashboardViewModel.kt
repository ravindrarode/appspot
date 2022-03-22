package com.appspot.mvvmarchitecture.ui.dashboard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appspot.mvvmarchitecture.data.repository.DashboardActivityMainRepository
import com.mindorks.retrofit.coroutines.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel(private val dashboardActivityMainRepository: DashboardActivityMainRepository) : ViewModel() {

    val resourceBalance = MutableLiveData<Resource<Any>>()
    val resourceTransaction = MutableLiveData<Resource<Any>>()

    fun getBalance(token: String) = viewModelScope.launch {

        resourceBalance.postValue(Resource.loading(data = null))

        try {
            val response = dashboardActivityMainRepository.getBalance(token)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    resourceBalance.postValue(Resource.success(data = response))
                } else {

                    var errorStr = ""
                    errorStr = try {
                        response.errorBody()!!.string()
                    } catch (e: java.lang.Exception) {
                        e.toString()
                    }
                    //Below two Error as per the old code. And add Delay because live
                    // data update string very fast so not able to show 2 toast message.
                    resourceBalance.postValue(
                        Resource.error(
                            data = null,
                            message = errorStr
                        )
                    )
                }
            }

        } catch (e: Exception) {
            resourceBalance.postValue(
                Resource.error(
                    data = null,
                    message = e.message ?: "Error Occurred!"
                )
            )
        }

    }

    fun getTransaction(token: String) = viewModelScope.launch {

        resourceTransaction.postValue(Resource.loading(data = null))

        try {
            val response = dashboardActivityMainRepository.getTransaction(token)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    resourceTransaction.postValue(Resource.success(data = response))
                } else {

                    var errorStr = ""
                    errorStr = try {
                        response.errorBody()!!.string()
                    } catch (e: java.lang.Exception) {
                        e.toString()
                    }
                    //Below two Error as per the old code. And add Delay because live
                    // data update string very fast so not able to show 2 toast message.
                    resourceTransaction.postValue(
                        Resource.error(
                            data = null,
                            message = errorStr
                        )
                    )
                }
            }

        } catch (e: Exception) {
            resourceTransaction.postValue(
                Resource.error(
                    data = null,
                    message = e.message ?: "Error Occurred!"
                )
            )
        }
    }

}