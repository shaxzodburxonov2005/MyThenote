package com.example.mythenote.databse

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun getAppDatabase(@ApplicationContext context: Context):UserDatabase{
        return Room.databaseBuilder(context,UserDatabase::class.java,"post_dp")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun providePostDao(userDatabase: UserDatabase):UserDao{
        return userDatabase.getUserDao()
    }
}