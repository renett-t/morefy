package ru.itis.morefy.core.domain.models


data class SpotifyUser(
    private val id: String,
    private val name: String,
    private val followerCount: Int,
    private val uri: String,

    private val country: String,
    private val email: String,
    private val imageUrl: String,
    private val subscription: String,
) {

    constructor(id: String, name: String, followerCount: Int, uri: String) :
            this(id, name, followerCount, uri, "", "", "", "") {
    }
}
