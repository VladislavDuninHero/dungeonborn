package com.game.dungeonborn.dto.character.inventory

data class AddToInventoryResponseDTO(
    var characterId: Long? = null,
    var inventory: InventoryDTO
)