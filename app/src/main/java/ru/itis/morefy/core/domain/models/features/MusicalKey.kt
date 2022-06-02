package ru.itis.morefy.core.domain.models.features

enum class MusicalKey(val key: String) {
    C("C"), CD("C♯/D♭"),
    D("D"), DE("D♯/E♭"),
    E("E"), F("F"),
    FG("F♯/G♭"), G("G"),
    GA("G♯/A♭"), A("A"),
    AB("A♯/B♭"), B("B"), UNDEFINED("-")
}
