package com.game.dungeonborn.service.utils.battle

import com.game.dungeonborn.dto.battle.AttackDTO
import com.game.dungeonborn.entity.character.Character
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class CharacterBattleService {
    fun attack(character: Character): AttackDTO {
        var totalDamage = character.characterStat?.totalDamage ?: 0.0;
        val criticalChance = character.characterStat?.totalCriticalChance ?: 0.0;
        val criticalPower = character.characterStat?.totalCriticalPower ?: 0.0;

        val isCriticalAttack = calculateCriticalAttack(criticalChance);
        if (isCriticalAttack) {
            totalDamage *= criticalPower;
        }

        return AttackDTO(
            totalDamage,
            criticalPower,
            isCritical = isCriticalAttack
        );
    }

    private fun calculateCriticalAttack(criticalChance: Double): Boolean {
        return Random.nextDouble(0.0, 100.0) <= criticalChance;
    }
}