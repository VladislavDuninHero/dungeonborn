package com.game.dungeonborn.repositories

import com.game.dungeonborn.entity.character.Character
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface CharacterRepository : JpaRepository<Character, Long> {

    @Query("FROM Character c WHERE c.id = :characterId")
    fun findCharacterById(id: Long): Optional<Character>
}