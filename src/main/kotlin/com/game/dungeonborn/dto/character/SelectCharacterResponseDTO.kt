package com.game.dungeonborn.dto.character

import com.game.dungeonborn.dto.dungeon.DungeonSlimDTO

data class SelectCharacterResponseDTO(
    val character: CharacterDTO,
    val availableDungeons: List<DungeonSlimDTO>,
)
