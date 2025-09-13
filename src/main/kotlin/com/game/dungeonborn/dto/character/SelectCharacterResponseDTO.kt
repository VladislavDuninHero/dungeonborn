package com.game.dungeonborn.dto.character

data class SelectCharacterResponseDTO(
    val character: CharacterDTO,
    val availableDungeons: String,
)
