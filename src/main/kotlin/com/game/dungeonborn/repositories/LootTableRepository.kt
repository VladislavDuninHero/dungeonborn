package com.game.dungeonborn.repositories

import com.game.dungeonborn.entity.loot.LootTable
import org.springframework.data.jpa.repository.JpaRepository

interface LootTableRepository : JpaRepository<LootTable, Long> {
}