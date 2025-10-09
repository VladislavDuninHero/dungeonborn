package com.game.dungeonborn.dto.npc

data class NpcDTO(
    var id: Long,
    var name: String,
    var lvl: Int,
    var isFriendly: Boolean,
    var isBoss: Boolean,
    var spawnChance: Double,
    var maxHp: Double,
    var maxMana: Double,
    var minDamage: Double,
    var maxDamage: Double,
    var criticalChance: Double,
    var criticalPower: Double,
    var armor: Double
)
