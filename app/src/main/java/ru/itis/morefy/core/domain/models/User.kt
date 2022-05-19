package ru.itis.morefy.core.domain.models


data class User(
    val id: String,
    val name: String,
    val followerCount: Int,
    val uri: String,

    val country: String,
    val email: String,
    val imageUrl: String,
    val subscription: String,
) {

    constructor(id: String, name: String, followerCount: Int, uri: String) :
            this(id, name, followerCount, uri, "", "", "", "") {
    }
}
