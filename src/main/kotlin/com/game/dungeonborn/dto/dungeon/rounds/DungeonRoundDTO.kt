package com.game.dungeonborn.entity.dungeon.rounds

import com.game.dungeonborn.dto.npc.NpcDTO
import java.util.UUID

data class DungeonRoundDTO (
    var npcs: MutableList<NpcDTO> = mutableListOf(),
    var roundMatrix: MutableList<MutableList<UUID?>> = MutableList(5) { MutableList(5) { null } }
)