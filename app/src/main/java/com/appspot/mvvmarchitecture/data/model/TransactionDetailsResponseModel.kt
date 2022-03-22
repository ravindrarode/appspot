package com.appspot.mvvmarchitecture.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TransactionDetailsResponseModel: Serializable {

    @SerializedName("status")
    var status:String = ""

    @SerializedName("data")
    var data: ArrayList<TransactionData> = ArrayList()

}

class TransactionData:Serializable{

    @SerializedName("transactionId")
    var transactionId: String = ""

    @SerializedName("amount")
    var amount: String = ""

    @SerializedName("transactionDate")
    var transactionDate: String = ""

    @SerializedName("description")
    var description: String = ""

    @SerializedName("transactionType")
    var transactionType: String = ""

    @SerializedName("receipient")
    var receipient: ReceipientData = ReceipientData()
}

class ReceipientData:Serializable{

    @SerializedName("accountNo")
    var accountNo: String = ""

    @SerializedName("accountHolder")
    var accountHolder: String = ""
}