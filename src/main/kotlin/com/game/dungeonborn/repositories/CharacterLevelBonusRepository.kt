package com.game.dungeonborn.repositories

import com.game.dungeonborn.entity.character.CharacterLevelBonus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface CharacterLevelBonusRepository : JpaRepository<CharacterLevelBonus, Long> {

    @Query("FROM CharacterLevelBonus clb WHERE clb.levelNumber = :level")
    fun findLevelByLevelNumber(level: Int): Optional<CharacterLevelBonus>
}