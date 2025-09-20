package com.game.dungeonborn.service.dungeon

import com.game.dungeonborn.dto.dungeon.DungeonDTO
import com.game.dungeonborn.dto.dungeon.DungeonLevelSlimDTO
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
    fun getAvailableDungeonsForCharacter(characterId: Long): List<DungeonDTO> {
        val foundCharacter = characterUtils.findCharacterById(characterId);
        val dungeons = dungeonRepository.findAll();

        return dungeons.map { dungeon ->
            DungeonDTO(
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
}