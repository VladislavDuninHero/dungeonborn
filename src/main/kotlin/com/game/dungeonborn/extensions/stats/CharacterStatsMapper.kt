package com.game.dungeonborn.extensions.stats

import com.game.dungeonborn.dto.character.CharacterStatsDTO
import com.game.dungeonborn.entity.character.CharacterStats
import org.springframework.stereotype.Component

@Component
class CharacterStatsMapper {

    fun toDTO(stats: CharacterStats): CharacterStatsDTO {
        return CharacterStatsDTO(
            id = stats.id ?: 0,
            totalHp = stats.totalHp ?: 0.0,
            totalMana = stats.totalMana ?: 0.0,
            totalStrength = stats.totalStrength ?: 0.0,
            totalIntellect = stats.totalIntellect ?: 0.0,
            totalAgility = stats.totalAgility ?: 0.0,
            totalCriticalChance = stats.totalCriticalChance ?: 0.0,
            totalCriticalPower = stats.totalCriticalPower ?: 0.0,
            totalGearScore = stats.totalGearScore ?: 0.0,
            totalDamage = stats.totalDamage ?: 0.0,
            totalArmor = stats.totalArmor ?: 0.0
        );
    }
}