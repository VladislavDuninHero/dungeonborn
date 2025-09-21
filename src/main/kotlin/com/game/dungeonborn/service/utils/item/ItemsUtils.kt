package com.game.dungeonborn.service.utils.item

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.entity.item.Item
import com.game.dungeonborn.exception.items.ItemNotFoundException
import com.game.dungeonborn.repositories.ItemsRepository
import org.springframework.stereotype.Service

@Service
class ItemsUtils(
    private val itemsRepository: ItemsRepository,
) {
    fun findItemByIdAndGet(itemId: Long): Item {
        return itemsRepository.findItemById(itemId).orElseThrow {
            throw ItemNotFoundException(ExceptionMessage.ITEM_NOT_FOUND)
        }
    }
}