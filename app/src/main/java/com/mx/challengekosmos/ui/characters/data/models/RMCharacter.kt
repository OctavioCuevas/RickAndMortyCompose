package com.mx.challengekosmos.ui.characters.data.models

import com.mx.challengekosmos.ui.characters.data.Origin

data class RMCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
)
