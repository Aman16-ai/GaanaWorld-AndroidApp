package com.example.gaanaworld.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val Name:String = "",
    val language : String = "",
    val thumbnail : String = "",
    val url : String = ""
):Parcelable
