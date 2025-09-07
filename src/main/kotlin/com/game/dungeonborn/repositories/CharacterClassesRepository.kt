package com.game.dungeonborn.repositories

import com.game.dungeonborn.entity.character.CharacterClass
import com.game.dungeonborn.enums.character.CharacterClass as CharacterClassName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CharacterClassesRepository : JpaRepository<CharacterClass, Long> {

    @Query("FROM CharacterClass cc WHERE cc.name = :className")
    fun findByClassName(className: CharacterClassName): CharacterClass?
}