package ru.itis.morefy.core.data.database.dao

import androidx.room.*
import ru.itis.morefy.core.domain.models.TableToken

@Dao
interface TokenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(tableToken: TableToken)

    @Query("SELECT * FROM token WHERE tokenId = :id")
    suspend fun getTokenById(id: Int): TableToken?

    @Query("SELECT accessToken FROM token")
    suspend fun getAccessToken(accessToken:String?): TableToken?

    @Query("DELETE FROM token")
    suspend fun deleteAllTokens()
}