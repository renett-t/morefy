package ru.itis.morefy.core.data.database.dao

import androidx.room.*
import ru.itis.morefy.core.domain.models.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(user: User)

    @Query("SELECT * FROM user WHERE user_id = :id")
    suspend fun getUserById(id: Int): User?

    @Query("SELECT access_token FROM USER")
    suspend fun getAccessToken(access_token:String?): User?

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()
}