package com.game.dungeonborn.dto.character.inventory

import jakarta.validation.constraints.NotNull

data class AddToInventoryDTO(
    @NotNull
    var characterId: Long,

    @NotNull
    var itemId: Long,
)
