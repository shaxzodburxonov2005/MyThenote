package com.example.mythenote.databse

import androidx.room.*
import com.example.mythenote.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Transaction
    @Query("SELECT * FROM informationDB")
    fun getTeethWithInfo(): List<InformationWithTeeth>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: Users)

    @Update
    fun updateTeeth(teeth: Teeth)

    @Query("SELECT * FROM Users ")
    fun getAllPost(): Flow<List<Users>>

    @Query("SELECT COUNT() FROM  Users Where firstName = :id")
    fun findArticleById(id: String): String

    @Transaction
    @Query("SELECT * FROM Users")
    fun getUserWithPlaylists(): Flow<List<UserWithInfo>>

    // boyagi rasm qob ketgan
    @Insert
    fun insertInfo(informationDB: InformationDB): Long

    @Insert
    suspend fun insertTeeth(list: List<Teeth>)

    @Query("SELECT * FROM informationDB ")
    fun getAllInfo(): Flow<List<InformationDB>>

    @Query("SELECT id FROM informationDB WHERE patient_sheet= :name")
    fun getIdByIdInfo(name: String): Int


    @Query("SELECT * FROM informationDB WHERE  info_id = :id ")
    fun getInfo(id: Int): Flow<List<InformationDB>>


    @Query("Select * From Users Where  firstName = :name")
    fun getSearchInfo(name: String): Flow<List<Users>>

    @Delete
    suspend fun deletedUser(users: Users)
}