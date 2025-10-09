package com.game.dungeonborn.dto.dungeon

data class RunDungeonResponseDTO(
    var characterId: Long,
    var dungeonId: Long,
    var dungeonLevelId: Long,
    var dungeonRunResult: BattleResultDTO,
    var dungeonLoot: DungeonLootDTO,
)