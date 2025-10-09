package com.game.dungeonborn.repositories

import com.game.dungeonborn.entity.dungeon.DungeonLevel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface DungeonLevelRepository : JpaRepository<DungeonLevel, Long> {

    @Query("FROM DungeonLevel dl WHERE dl.dungeon.id = :dungeonId")
    fun findLevelsByDungeonId(dungeonId: Long): List<DungeonLevel>

    @Query("FROM DungeonLevel dl WHERE dl.dungeon.id = :dungeonId AND dl.id = :dungeonLevelId")
    fun findDungeonLevelByIdAndDungeonId(dungeonId: Long, dungeonLevelId: Long): Optional<DungeonLevel>

    @Query(
        "FROM DungeonLevel dl " +
                "LEFT JOIN FETCH dl.dungeonLoot dloot " +
                "LEFT JOIN FETCH dloot.lootTable lt " +
                "LEFT JOIN FETCH lt.lootTableItems lti " +
                "LEFT JOIN FETCH lti.item item " +
                "WHERE dl.id = :id"
    )
    fun findDungeonLevelById(id: Long): Optional<DungeonLevel>
}