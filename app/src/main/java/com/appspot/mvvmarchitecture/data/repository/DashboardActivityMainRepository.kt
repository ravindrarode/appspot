package com.appspot.mvvmarchitecture.data.repository

import com.appspot.mvvmarchitecture.data.api.ApiHelper

class DashboardActivityMainRepository(private val apiHelper: ApiHelper) {

    suspend fun getBalance(token:String) = apiHelper.getBalance(token)
    suspend fun getTransaction(token:String) = apiHelper.getTransaction(token)
}