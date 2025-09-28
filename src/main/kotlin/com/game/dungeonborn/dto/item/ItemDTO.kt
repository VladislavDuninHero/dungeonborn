package com.game.dungeonborn.dto.item

import com.game.dungeonborn.enums.item.ItemQuality
import com.game.dungeonborn.enums.item.ItemType
import com.game.dungeonborn.enums.item.SlotType

data class ItemDTO(
    var itemId: Long,
    var name: String,
    var type: ItemType,
    var slot: SlotType,
    var itemLevel: Double,
    var quality: ItemQuality,
    var stamina: Double,
    var strength: Double,
    var intellect: Double,
    var agility: Double,
    var criticalChance: Double,
    var criticalPower: Double,
    var armor: Double
)