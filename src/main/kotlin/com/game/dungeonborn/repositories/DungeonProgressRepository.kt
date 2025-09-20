package com.game.dungeonborn.repositories

import com.game.dungeonborn.entity.dungeon.DungeonProgress
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DungeonProgressRepository : JpaRepository<DungeonProgress, Long> {

    @Query("FROM DungeonProgress d WHERE d.character.id = :characterId")
    fun findDungeonsProgressForCharacterAndGet(characterId: Long): List<DungeonProgress>
}