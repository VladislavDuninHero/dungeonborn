package com.game.dungeonborn.repositories

import com.game.dungeonborn.entity.character.CharacterEquipment
import org.springframework.data.jpa.repository.JpaRepository

interface CharacterEquipmentRepository : JpaRepository<CharacterEquipment, Long> {

}