package com.game.dungeonborn.dto.character.equipment

import com.game.dungeonborn.enums.item.SlotType
import jakarta.validation.constraints.NotNull

data class DeleteFromEquipmentDTO(
    @NotNull
    var characterId: Long,

    @NotNull
    var slotType: SlotType,
)