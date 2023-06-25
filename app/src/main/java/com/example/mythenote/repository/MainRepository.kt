package com.example.mythenote.repository

import com.example.mythenote.databse.UserDao
import com.example.mythenote.model.InformationDB
import com.example.mythenote.model.Teeth
import com.example.mythenote.model.UserWithInfo
import com.example.mythenote.model.Users
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun insertPost(users: Users) {
        userDao.insertUsers(users)
    }

    fun updateTeeth(teeth: Teeth) = userDao.updateTeeth(teeth)

    fun getAllPost(): Flow<List<Users>> = userDao.getAllPost()

    fun getAllTeeth() = userDao.getTeethWithInfo()

    fun findById(id: String) = userDao.findArticleById(id)

    fun getAllInfo(): Flow<List<InformationDB>> = userDao.getAllInfo()


    fun getUserWithInfo(): Flow<List<UserWithInfo>> = userDao.getUserWithPlaylists()

    fun insertInfo(informationDB: InformationDB): Long {
        return userDao.insertInfo(informationDB)
    }

    suspend fun insertTeeth(teeth: List<Teeth>) {
        userDao.insertTeeth(teeth)
    }

    fun getIdByName(id: String) = userDao.getIdByIdInfo(id)


    fun getIdByInfo(id: Int): Flow<List<InformationDB>> = userDao.getInfo(id)

    fun getSearch(name: String): Flow<List<Users>> = userDao.getSearchInfo(name)

    suspend fun userDeleted(users: Users){
        userDao.deletedUser(users)
    }
}