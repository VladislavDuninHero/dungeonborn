package com.game.dungeonborn.dto.character.equipment

import com.game.dungeonborn.dto.character.inventory.InventoryDTO

data class DeleteFromEquipmentAndMoveToInventoryResponseDTO(
    var equipmentDTO: EquipmentDTO,
    var inventory: InventoryDTO
)