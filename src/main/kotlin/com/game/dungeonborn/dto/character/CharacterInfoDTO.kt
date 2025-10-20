package com.game.dungeonborn.dto.character

data class CharacterInfoDTO(
    var characterId: Long,
    var level: Int,
    var isLevelUp: Boolean = false,
)
