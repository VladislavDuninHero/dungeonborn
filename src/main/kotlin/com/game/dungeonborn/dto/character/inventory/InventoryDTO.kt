package com.game.dungeonborn.dto.character.inventory


data class InventoryDTO(
    var inventoryId: Long? = null,
    var items: List<InventoryItemDTO>
)
