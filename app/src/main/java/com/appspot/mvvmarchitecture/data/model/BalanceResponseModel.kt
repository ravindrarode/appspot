package com.appspot.mvvmarchitecture.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BalanceResponseModel:Serializable {

    @SerializedName("status")
    var status:String = ""

    @SerializedName("accountNo")
    var accountNo:String = ""

    @SerializedName("balance")
    var balance:String = ""
}