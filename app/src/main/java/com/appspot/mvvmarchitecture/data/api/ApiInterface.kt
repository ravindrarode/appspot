package com.appspot.mvvmarchitecture.data.api

import com.appspot.mvvmarchitecture.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    /**
     * The return type is important here
     * The class structure that you've defined in Call<T>
     * should exactly match with your json response
     * If you are not using another api, and using the same as mine
     * then no need to worry, but if you have your own API, make sure
     * you change the return type appropriately
     **/


    @POST("login")
    suspend fun getLoginDetails(@Header("Content-Type") content_type:String,
                                @Header("Accept") accept:String,
                                @Body data: LoginRequestModel
    ): Response<LoginResponseModel>

    @GET
    suspend fun getBalance(@Header("Content-Type") content_type:String,
                           @Header("Accept") accept:String,
                           @Header("Authorization") token:String, @Url balanceURL:String): Response<BalanceResponseModel>

    @GET
    suspend fun getTransactions(@Header("Content-Type") content_type:String,
                           @Header("Accept") accept:String,
                           @Header("Authorization") token:String, @Url balanceURL:String): Response<TransactionDetailsResponseModel>
}