package com.example.gaanaworld.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Singers(
    var id : String? = "",
    val name:String?="",
    val profileImgUrl:String = ""
):Parcelable