package com.game.dungeonborn.service.dungeon

import com.game.dungeonborn.dto.dungeon.DungeonDTO
import com.game.dungeonborn.dto.dungeon.DungeonLevelDTO
import com.game.dungeonborn.dto.dungeon.DungeonLevelSlimDTO
import com.game.dungeonborn.dto.dungeon.DungeonSlimDTO
import com.game.dungeonborn.repositories.DungeonRepository
import org.springframework.stereotype.Service

@Service
class DungeonService(
    private val dungeonRepository: DungeonRepository,
    private val dungeonUtils: DungeonUtils
) {

    fun getDungeon(id: Long): DungeonDTO {
        val foundDungeon = dungeonUtils.findDungeonByIdAndGet(id);

        val dungeon = DungeonDTO(
            foundDungeon.id ?: 0,
            foundDungeon.name,
            foundDungeon.dungeonLevels.map {
                DungeonLevelDTO(
                    it.id,
                    it.lvl,
                    it.recommendedPower,
                    it.totalEnemiesNpc,
                    it.ratingPoints,
                    it.dungeonRounds,
                    it.dungeon?.id,
                    it.dungeonLoot?.id
                )
            }
        );

        return dungeon;
    }

    fun getAllDungeons(): List<DungeonSlimDTO> {
        val dungeons = dungeonRepository.findAll();

        return dungeons.map { dungeon ->
            DungeonSlimDTO(
                dungeon.id ?: 0,
                dungeon.name,
                dungeon.dungeonLevels.map {
                    DungeonLevelSlimDTO(
                        it.id,
                        it.lvl,
                        it.recommendedPower,
                    )
                }
            )
        };
    }
}