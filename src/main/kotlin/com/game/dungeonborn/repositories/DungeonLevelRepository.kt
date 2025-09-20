package com.game.dungeonborn.repositories

import com.game.dungeonborn.entity.dungeon.DungeonLevel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DungeonLevelRepository : JpaRepository<DungeonLevel, Long> {


}