package com.game.dungeonborn.dto.dungeon


data class DungeonLevelDTO(
    var id: Long? = 0,
    var lvl: Int? = 0,
    var recommendedPower: Double? = 0.0,
    var totalEnemiesNpc: Int? = 0,
    var ratingPoints: Double? = 0.0,
    var dungeonRounds: Int? = 0,
    var dungeonId: Long? = 0,
    var dungeonLootId: Long? = 0,
) {
}