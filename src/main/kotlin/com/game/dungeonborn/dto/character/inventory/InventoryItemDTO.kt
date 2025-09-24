package com.game.dungeonborn.dto.character.inventory

import com.game.dungeonborn.dto.item.ItemDTO

data class InventoryItemDTO(
    var id: Long,
    var item: ItemDTO
)
