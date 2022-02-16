package com.example.gaanaworld.data.model

data class User(
    var uid:String?=null,
    var firstName:String?=null,
    var lastName:String?=null,
    var email:String?=null,
    var singers: List<Singers>?=null
)