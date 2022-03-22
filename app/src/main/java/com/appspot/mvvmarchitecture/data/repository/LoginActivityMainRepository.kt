package com.appspot.mvvmarchitecture.data.repository

import com.appspot.mvvmarchitecture.data.api.ApiHelper
import com.appspot.mvvmarchitecture.data.model.LoginRequestModel

class LoginActivityMainRepository(private val apiHelper: ApiHelper) {

    suspend fun getLoginDetails(data: LoginRequestModel) = apiHelper.getLogin(data)
}