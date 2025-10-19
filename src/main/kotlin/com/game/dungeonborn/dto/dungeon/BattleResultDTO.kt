package com.game.dungeonborn.dto.dungeon

import com.game.dungeonborn.dto.battle.BattleStepDTO
import com.game.dungeonborn.dto.dungeon.rounds.DungeonRoundBattleResultDTO

data class BattleResultDTO(
    var initCharacterHp: Double,
    var initCharacterMana: Double,
    var characterIsAlive: Boolean,
    var rounds: MutableMap<Int, DungeonRoundBattleResultDTO>
)
