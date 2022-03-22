package com.appspot.mvvmarchitecture.config.client

import com.appspot.mvvmarchitecture.data.api.ApiInterface
import com.appspot.mvvmarchitecture.utils.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClent {

    fun getAPIInterface(): ApiInterface {

        val retrofit = Retrofit.Builder().baseUrl(Constant.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(ApiInterface::class.java)
    }
}