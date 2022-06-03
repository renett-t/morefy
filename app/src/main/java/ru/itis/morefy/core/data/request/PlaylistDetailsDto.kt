package ru.itis.morefy.core.data.request

import com.google.gson.annotations.SerializedName

data class PlaylistDetailsDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("public")
    val public: Boolean,
    @SerializedName("collaborative")
    val collaborative: Boolean,
    @SerializedName("description")
    val description: String
)
