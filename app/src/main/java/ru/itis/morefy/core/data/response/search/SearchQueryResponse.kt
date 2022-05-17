package ru.itis.morefy.core.data.response.search

import ru.itis.morefy.core.data.response.search.album.Albums
import ru.itis.morefy.core.data.response.search.artist.Artists
import ru.itis.morefy.core.data.response.search.playlist.Playlists
import ru.itis.morefy.core.data.response.search.track.Tracks

data class SearchQueryResponse(
    val albums: Albums,
    val artists: Artists,
    val playlists: Playlists,
    val tracks: Tracks
)
