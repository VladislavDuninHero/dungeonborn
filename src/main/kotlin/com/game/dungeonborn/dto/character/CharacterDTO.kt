package com.game.dungeonborn.dto.character

import com.game.dungeonborn.dto.character.inventory.InventoryItemDTO
import com.game.dungeonborn.dto.item.ItemDTO
import com.game.dungeonborn.enums.character.CharacterClass

data class CharacterDTO(
    var id: Long,
    var name: String,
    var level: Int,
    var experience: Double,
    var characterClass: CharacterClass?,
    var characterStats: CharacterStatsDTO,
    var equipment: List<ItemDTO>,
    var inventory: List<InventoryItemDTO>
)