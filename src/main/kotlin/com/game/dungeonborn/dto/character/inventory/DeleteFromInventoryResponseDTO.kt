package com.game.dungeonborn.dto.character.inventory

import com.game.dungeonborn.dto.item.ItemDTO

data class DeleteFromInventoryResponseDTO(
    var inventoryId: Long,
    var inventory: List<ItemDTO>
)
