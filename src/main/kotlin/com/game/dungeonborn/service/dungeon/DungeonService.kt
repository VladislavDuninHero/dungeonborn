package com.game.dungeonborn.service.dungeon

import com.game.dungeonborn.dto.dungeon.DungeonDTO
import com.game.dungeonborn.dto.dungeon.DungeonSlimDTO
import com.game.dungeonborn.repositories.DungeonRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class DungeonService(
    private val dungeonRepository: DungeonRepository,
) {

//    fun getDungeon(id: Long): DungeonDTO {
//        val dungeon = DungeonDTO(i);
//
//        return dungeon;
//    }

    fun getAllDungeons(): List<DungeonSlimDTO> {
        val dungeons = dungeonRepository.findAll();

        return dungeons.map { DungeonSlimDTO(it.id ?: 0, it.name) };
    }
}