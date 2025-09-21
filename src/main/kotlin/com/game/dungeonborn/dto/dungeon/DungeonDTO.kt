package com.game.dungeonborn.dto.dungeon

data class DungeonDTO(
    var id: Long,
    var name: String,
    var dungeonLevels: List<DungeonLevelDTO>,
)