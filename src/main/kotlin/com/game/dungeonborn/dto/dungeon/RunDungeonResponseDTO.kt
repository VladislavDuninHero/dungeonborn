package com.game.dungeonborn.dto.dungeon

import com.game.dungeonborn.dto.character.CharacterInfoDTO

data class RunDungeonResponseDTO(
    var characterInfo: CharacterInfoDTO,
    var dungeonId: Long,
    var dungeonLevelId: Long,
    var dungeonRunResult: BattleResultDTO,
    var dungeonLoot: DungeonLootDTO,
)