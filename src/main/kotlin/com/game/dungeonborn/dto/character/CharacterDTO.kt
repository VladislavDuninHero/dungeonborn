package com.game.dungeonborn.dto.character

import com.game.dungeonborn.dto.item.ItemDTO
import com.game.dungeonborn.enums.character.CharacterClass

data class CharacterDTO(
    var id: Long,
    var name: String,
    var level: Int,
    var characterClass: CharacterClass?,
    var equipment: List<ItemDTO>
)