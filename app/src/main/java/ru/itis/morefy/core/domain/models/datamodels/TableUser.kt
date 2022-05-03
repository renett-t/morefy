package ru.itis.morefy.core.domain.models.datamodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class TableUser(
    @PrimaryKey
    var userId: Int,
    @ColumnInfo(name = "userSubscribersCount")
    var userSubscribersCount: Int,
    @ColumnInfo(name = "userSavedAlbumsCount")
    var userSavedAlbumsCount: Int,
    @ColumnInfo(name = "userSubscriptionsCount")
    var userSubscriptionsCount: Int,
) {
}