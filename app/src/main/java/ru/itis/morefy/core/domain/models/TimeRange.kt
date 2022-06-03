package ru.itis.morefy.core.domain.models

enum class TimeRange(val time: String) {
    LONG("long_term"),
    MEDIUM("medium_term"),
    SHORT("short_term"),
    UNDEFINED("-")
}
