package com.appspot.mvvmarchitecture.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LoginResponseModel : Serializable {

    @SerializedName("status")
    var status: String = ""

    @SerializedName("token")
    var token: String = ""

    @SerializedName("username")
    var username: String = ""

    @SerializedName("accountNo")
    var accountNo: String = ""

}