package com.game.dungeonborn.dto.battle

data class BattleStepDTO(
    val stepNumber: Int,
    val characterAttack: AttackDTO,
    val npcAttack: AttackDTO,
    val characterHp: Double,
    val npcHp: Double,
)