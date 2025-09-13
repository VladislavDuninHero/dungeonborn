package com.game.dungeonborn.dto.character

data class CharacterStatsDTO(
    var id: Long,
    var totalHp: Double,
    var totalMana: Double,
    var totalStrength: Double,
    var totalIntellect: Double,
    var totalAgility: Double,
    var totalCriticalChance: Double,
    var totalCriticalPower: Double,
    var totalGearScore: Double,
    var totalDamage: Double,
    var totalArmor: Double
)
