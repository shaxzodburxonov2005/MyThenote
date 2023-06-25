package com.example.mythenote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Users(
                 var firstName:String,
                 var lastName:String,
                 var address:String,
                 var phoneNumber:String,
                 var birthDay:String,
                 var img:String?=null,
                 @PrimaryKey(autoGenerate = true)
                 var id:Int?=null,
                 ):java.io.Serializable
