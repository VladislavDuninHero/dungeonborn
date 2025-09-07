package com.game.dungeonborn.dto.character

data class CreateCharacterResponseDTO(
    val id: Long,
    val userId: Long,
    val name: String,
    val level: Int
) {
}