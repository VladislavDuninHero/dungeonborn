package com.game.dungeonborn.dto.character.equipment

import jakarta.validation.constraints.NotNull

data class AddToEquipmentDTO(
    @NotNull
    var characterId: Long,

    @NotNull
    var inventoryItemId: Long
)