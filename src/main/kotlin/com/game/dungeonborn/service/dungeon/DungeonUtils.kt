package com.game.dungeonborn.service.dungeon

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.dto.dungeon.DungeonDTO
import com.game.dungeonborn.dto.dungeon.DungeonLevelSlimDTO
import com.game.dungeonborn.dto.dungeon.DungeonSlimDTO
import com.game.dungeonborn.entity.dungeon.Dungeon
import com.game.dungeonborn.exception.character.CharacterNotFoundException
import com.game.dungeonborn.exception.dungeon.DungeonNotFoundException
import com.game.dungeonborn.repositories.DungeonLevelRepository
import com.game.dungeonborn.repositories.DungeonProgressRepository
import com.game.dungeonborn.repositories.DungeonRepository
import com.game.dungeonborn.service.utils.character.CharacterUtils
import org.springframework.stereotype.Service

@Service
class DungeonUtils(
    private val characterUtils: CharacterUtils,
    private val dungeonLevelRepository: DungeonLevelRepository,
    private val dungeonProgressRepository: DungeonProgressRepository,
    private val dungeonRepository: DungeonRepository
) {
    fun getAvailableDungeonsForCharacter(characterId: Long): List<DungeonSlimDTO> {
        val foundCharacter = characterUtils.findCharacterById(characterId);
        val dungeons = dungeonRepository.findAll();

        return dungeons.map { dungeon ->
            DungeonSlimDTO(
                id = dungeon.id ?: 0,
                name = dungeon.name,
                dungeonLevels = dungeon.dungeonLevels
                    .map {
                        DungeonLevelSlimDTO(
                            id = it.id,
                            lvl = it.lvl,
                            recommendedPower = it.recommendedPower
                        )
                    }
            )
        }
    }

    fun findDungeonByIdAndGet(id: Long): Dungeon {
        return dungeonRepository.findById(id)
            .orElseThrow { throw DungeonNotFoundException(ExceptionMessage.DUNGEON_NOT_FOUND) };
    }
}