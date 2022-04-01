package ru.itis.morefy.core.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "token",foreignKeys = [
    ForeignKey(entity = TableUser::class,
        parentColumns = ["userId"],
        childColumns = ["userId"],
        onDelete = CASCADE)
])
data class TableToken(
    @PrimaryKey
    var tokenId: Int,
    @ColumnInfo
    var userId: Int,
    @ColumnInfo(name = "accessToken")
    var accessToken: String?,
    @ColumnInfo(name = "whenGranted")
    var whenGranted: Int?,
    @ColumnInfo(name = "expiresIn")
    var expiresIn: Int?,
    @ColumnInfo(name = "refreshToken")
    var refreshToken: Boolean?,
){
}