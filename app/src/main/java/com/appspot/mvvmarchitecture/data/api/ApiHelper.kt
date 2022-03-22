package com.appspot.mvvmarchitecture.data.api

import com.appspot.mvvmarchitecture.data.model.BalanceResponseModel
import com.appspot.mvvmarchitecture.data.model.LoginRequestModel
import com.appspot.mvvmarchitecture.data.model.LoginResponseModel
import com.appspot.mvvmarchitecture.data.model.TransactionDetailsResponseModel
import retrofit2.Response

class ApiHelper(private val apiService: ApiInterface) {

    suspend fun getLogin(data: LoginRequestModel): Response<LoginResponseModel> = apiService.getLoginDetails("application/json", "application/json", data)
    suspend fun getBalance(token: String): Response<BalanceResponseModel> = apiService.getBalance("application/json", "application/json", token, "https://green-thumb-64168.uc.r.appspot.com/balance")
    suspend fun getTransaction(token: String): Response<TransactionDetailsResponseModel> = apiService.getTransactions("application/json", "application/json", token, "https://green-thumb-64168.uc.r.appspot.com/transactions")
}