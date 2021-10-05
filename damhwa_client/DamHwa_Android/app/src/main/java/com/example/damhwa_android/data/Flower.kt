package com.example.damhwa_android.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Flower(
    @SerializedName("fno")
    val fno: Int,
    @SerializedName("fname_KR")
    val fNameKR: String,
    @SerializedName("fname_EN")
    val fNameEN: String,
    @SerializedName("fmonth")
    val fMonth: Int,
    @SerializedName("fday")
    val fDay: Int,
    @SerializedName("flang")
    val fLang: String,
    @SerializedName("fcontents")
    val fContents: String,
    @SerializedName("fuse")
    val fUse: String,
    @SerializedName("fgrow")
    val fGrow: String,
    @SerializedName("img1")
    val img1: String,
    @SerializedName("img2")
    val img2: String,
    @SerializedName("img3")
    val img3: String,
    @SerializedName("watercolor_img")
    val watercolor_img: String
): Serializable
