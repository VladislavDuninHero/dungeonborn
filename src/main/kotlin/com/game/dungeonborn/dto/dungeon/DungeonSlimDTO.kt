package com.game.dungeonborn.dto.dungeon

data class DungeonSlimDTO(
    var id: Long,
    var name: String,
    var dungeonLevels: List<DungeonLevelSlimDTO>,
)
