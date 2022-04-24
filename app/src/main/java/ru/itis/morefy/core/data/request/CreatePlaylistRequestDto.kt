package ru.itis.morefy.core.data.request

data class CreatePlaylistRequestDto (
  private val name: String,
  private val public: Boolean,
  private val collaborative: Boolean,
  private val description: String,
)
