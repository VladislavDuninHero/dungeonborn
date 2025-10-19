package com.game.dungeonborn.dto.battle

import java.util.UUID

data class BattleStepDTO(
    val stepNumber: Int,
    val characterAttack: AttackDTO,
    val npcAttack: AttackDTO,
    val characterHp: Double,
    val npcId: Long,
    val npcInstanceId: UUID,
    val npcInitHp: Double,
    val npcHp: Double,
)