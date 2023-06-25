package com.example.mythenote.databse

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mythenote.model.InformationDB
import com.example.mythenote.model.Teeth

import com.example.mythenote.model.Users

@Database(entities = [Users::class, InformationDB::class, Teeth::class], version = 2)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}
