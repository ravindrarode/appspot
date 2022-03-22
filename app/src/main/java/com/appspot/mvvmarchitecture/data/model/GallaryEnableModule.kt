package com.appspot.mvvmarchitecture.data.model

import com.google.gson.annotations.SerializedName

class  GallaryEnableModule {

    @SerializedName("enableGallary")
    var enableGallary: Boolean = false

    @SerializedName("docUpload")
    var docUpload: Boolean = false

    constructor() {}
    constructor(
        enableGallary: Boolean?,
        docUpload: Boolean?,

        ) {
        if (enableGallary != null) {
            this.enableGallary = enableGallary
        }
        if (docUpload != null) {
            this.docUpload = docUpload
        }
    }

}