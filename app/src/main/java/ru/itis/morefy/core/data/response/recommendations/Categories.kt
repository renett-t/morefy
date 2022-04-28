package ru.itis.morefy.core.data.response.recommendations

data class Categories(
    val href: String,
    val items: List<CategoryItem>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: Any,
    val total: Int
)
