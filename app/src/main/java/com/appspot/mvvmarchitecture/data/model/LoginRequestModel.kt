package com.appspot.mvvmarchitecture.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LoginRequestModel : Serializable {

    @SerializedName("username")
    var username: String = ""

    @SerializedName("password")
    var password: String = ""
}