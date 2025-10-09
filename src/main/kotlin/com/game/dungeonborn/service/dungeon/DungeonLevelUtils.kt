package com.game.dungeonborn.service.dungeon

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.entity.dungeon.DungeonLevel
import com.game.dungeonborn.exception.dungeon.DungeonLevelNotFoundException
import com.game.dungeonborn.repositories.DungeonLevelRepository
import org.springframework.stereotype.Service

@Service
class DungeonLevelUtils(
    private val dungeonLevelRepository: DungeonLevelRepository
) {
    fun findDungeonLevelById(id: Long): DungeonLevel {
        return dungeonLevelRepository.findDungeonLevelById(id)
            .orElseThrow {
                throw DungeonLevelNotFoundException(ExceptionMessage.DUNGEON_LEVEL_NOT_FOUND);
            };
    }
}