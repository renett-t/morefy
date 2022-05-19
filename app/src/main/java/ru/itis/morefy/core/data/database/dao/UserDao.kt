package ru.itis.morefy.core.data.database.dao

import androidx.room.*
import ru.itis.morefy.core.domain.models.datamodels.TableUser

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(tableUser: TableUser)

    @Query("SELECT * FROM user WHERE userId = :id")
    suspend fun getUserById(id: Int): TableUser?

    @Delete
    suspend fun deleteUser(tableUser: TableUser)

    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()
}