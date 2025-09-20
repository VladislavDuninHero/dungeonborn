package com.game.dungeonborn.repositories

import com.game.dungeonborn.entity.loot.LootTableItems
import org.springframework.data.jpa.repository.JpaRepository

interface LootTableItemsRepository : JpaRepository<LootTableItems, Long> {
}