package ru.itis.morefy.core.data.response.recommendations

import ru.itis.morefy.core.data.response.common.Image

data class CategoryItem(
    val href: String,
    val icons: List<Image>,
    val id: String,
    val name: String
)
