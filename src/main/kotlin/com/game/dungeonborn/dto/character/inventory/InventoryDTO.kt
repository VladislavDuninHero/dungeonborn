package com.game.dungeonborn.dto.character.inventory

import com.game.dungeonborn.dto.item.ItemDTO

data class InventoryDTO(
    var inventoryId: Long? = null,
    var items: List<ItemDTO>
)
