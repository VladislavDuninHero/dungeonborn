package com.game.dungeonborn.dto.character

import com.game.dungeonborn.enums.character.CharacterClass

data class CharacterSlimDTO(
    var id: Long,
    var name: String,
    var level: Int,
    var characterClass: CharacterClass?,
)
