package com.game.dungeonborn.repositories

import com.game.dungeonborn.entity.dungeon.Dungeon
import org.springframework.data.jpa.repository.JpaRepository

interface DungeonRepository : JpaRepository<Dungeon, Long> {
}