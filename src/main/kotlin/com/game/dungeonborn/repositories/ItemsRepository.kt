package com.game.dungeonborn.repositories

import com.game.dungeonborn.entity.item.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ItemsRepository : JpaRepository<Item, Long> {

    @Query("FROM Item i WHERE i.id = :id")
    fun findItemById(id: Long): Optional<Item>;
}