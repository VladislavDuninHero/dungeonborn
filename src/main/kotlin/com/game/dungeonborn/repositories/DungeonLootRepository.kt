package com.game.dungeonborn.repositories

import com.game.dungeonborn.entity.dungeon.DungeonLoot
import org.springframework.data.jpa.repository.JpaRepository

interface DungeonLootRepository : JpaRepository<DungeonLoot, Long> {
}