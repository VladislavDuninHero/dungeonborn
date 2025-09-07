package com.game.dungeonborn.repositories

import com.game.dungeonborn.entity.character.CharacterStats
import org.springframework.data.jpa.repository.JpaRepository

interface CharacterStatsRepository : JpaRepository<CharacterStats, Long> {
}