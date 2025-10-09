package com.game.dungeonborn.dto.dungeon

import jakarta.validation.constraints.NotNull

data class RunDungeonDTO(

    @NotNull
    var characterId: Long,

    @NotNull
    var dungeonId: Long,

    @NotNull
    var dungeonLevelId: Long
)
