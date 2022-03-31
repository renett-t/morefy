package ru.itis.morefy.core.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    var user_id: Int,
    @ColumnInfo(name = "access_token")
    var access_token: String?,
    @ColumnInfo(name = "when_granted")
    var when_granted: Int?,
    @ColumnInfo(name = "expires_in")
    var expires_in: Int?,
    @ColumnInfo(name = "refresh_token")
    var refresh_token: Boolean?,
) {
}