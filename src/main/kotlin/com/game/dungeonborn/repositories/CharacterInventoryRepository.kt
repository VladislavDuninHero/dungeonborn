package com.game.dungeonborn.repositories

import com.game.dungeonborn.entity.character.CharacterInventory
import org.springframework.data.jpa.repository.JpaRepository

interface CharacterInventoryRepository : JpaRepository<CharacterInventory, Long> {

}