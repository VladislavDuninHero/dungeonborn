package com.game.dungeonborn.dto.character

import jakarta.validation.constraints.NotNull

data class SelectCharacterDTO(
    @NotNull
    val characterId: Long,
)
