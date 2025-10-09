package com.game.dungeonborn.dto.dungeon

import com.game.dungeonborn.dto.battle.BattleStepDTO

data class BattleResultDTO(
    var characterIsAlive: Boolean,
    var rounds: MutableMap<Int, MutableList<BattleStepDTO>>
)
