package com.game.dungeonborn.service.utils.inventory

import com.game.dungeonborn.constant.DefaultGameConfig
import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.entity.character.CharacterInventory
import com.game.dungeonborn.entity.character.CharacterInventoryItem
import com.game.dungeonborn.exception.character.InventoryIsFullException
import com.game.dungeonborn.exception.inventory.InventoryNotFoundException
import com.game.dungeonborn.repositories.CharacterInventoryRepository
import org.springframework.stereotype.Service

@Service
class CharacterInventoryUtils(
    private val characterInventoryRepository: CharacterInventoryRepository,
) {
    fun getCharacterInventoryByIdAndGet(id: Long): CharacterInventory {
        return characterInventoryRepository.findById(id).orElseThrow {
            throw InventoryNotFoundException(ExceptionMessage.INVENTORY_NOT_FOUND);
        }
    }

    fun addItemToInventory(inventory: MutableList<CharacterInventoryItem>, item: CharacterInventoryItem)
    : List<CharacterInventoryItem> {
        if (inventory.size >= DefaultGameConfig.MAX_INVENTORY_SIZE) {
            throw InventoryIsFullException(ExceptionMessage.INVENTORY_IS_FULL)
        }

        inventory.add(item);

        return inventory;
    }
}