package com.game.dungeonborn.dto.npc

import java.util.UUID


data class NpcDTO(
    var id: Long,
    var instanceId: UUID? = null,
    var name: String,
    var lvl: Int,
    var isFriendly: Boolean,
    var isBoss: Boolean,
    var spawnChance: Double,
    var initHp: Double,
    var maxHp: Double,
    var maxMana: Double,
    var minDamage: Double,
    var maxDamage: Double,
    var criticalChance: Double,
    var criticalPower: Double,
    var armor: Double
)
