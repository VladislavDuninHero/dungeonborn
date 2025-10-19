package com.game.dungeonborn.service.utils.battle

import com.game.dungeonborn.dto.battle.AttackDTO
import com.game.dungeonborn.dto.npc.NpcDTO
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class NpcBattleService {
    fun attack(npc: NpcDTO): AttackDTO {
        var totalDamage = arrayOf(npc.minDamage, npc.maxDamage).random();
        val criticalChance = npc.criticalChance;
        val criticalPower = npc.criticalPower;

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