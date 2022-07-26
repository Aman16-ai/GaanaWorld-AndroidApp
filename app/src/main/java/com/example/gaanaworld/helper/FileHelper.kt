package com.example.gaanaworld.helper

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.google.android.exoplayer2.util.MimeTypes

fun getFileExtension(context:Context,uri:Uri):String? {
    val contentResolver = context.contentResolver
    val mimeTypeMap = MimeTypeMap.getSingleton()
    return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
}

fun isImageFile(type:String):Boolean {
    return type.lowercase() == "jpg" || type.lowercase() == "png" || type.lowercase() == "jpeg"
}

fun isSongFile(type:String):Boolean {
    return type.lowercase() == "mp3"
}