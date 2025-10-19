package com.game.dungeonborn.dto.dungeon.rounds

import com.game.dungeonborn.dto.battle.BattleStepDTO
import java.util.*

data class DungeonRoundBattleResultDTO(
    var roundMatrix: MutableList<MutableList<UUID?>>,
    var roundSteps: MutableList<BattleStepDTO>
)
