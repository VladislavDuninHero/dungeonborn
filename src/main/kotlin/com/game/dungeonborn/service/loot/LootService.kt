package com.game.dungeonborn.service.loot

import com.game.dungeonborn.entity.loot.LootTableItems
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class LootService {
    fun calculateDrop(lootTables: MutableList<LootTableItems>): MutableList<LootTableItems> {
        return lootTables
            .filter { calculateDrop(it.item?.dropChance ?: 0.0) }
            .toMutableList();
    }

    private fun calculateDrop(dropChance: Double): Boolean {
        return Random.nextDouble(0.0, 100.0) >= dropChance;
    }
}