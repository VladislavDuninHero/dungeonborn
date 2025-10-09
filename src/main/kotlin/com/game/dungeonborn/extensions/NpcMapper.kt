package com.game.dungeonborn.extensions

import com.game.dungeonborn.dto.npc.NpcDTO
import com.game.dungeonborn.entity.npc.Npc
import org.springframework.stereotype.Component

@Component
class NpcMapper {
    fun toDTO(npc: Npc): NpcDTO {
        return NpcDTO(
            id = npc.id ?: 0,
            name = npc.name ?: "Lich King",
            lvl = npc.lvl ?: 0,
            isFriendly = npc.isFriendly ?: false,
            spawnChance = npc.spawnChance ?: 0.0,
            maxHp = npc.maxHp ?: 0.0,
            maxMana = npc.maxMana ?: 0.0,
            isBoss = npc.isBoss ?: false,
            minDamage = npc.minDamage ?: 0.0,
            maxDamage = npc.maxDamage ?: 0.0,
            criticalChance = npc.criticalChance ?: 0.0,
            criticalPower = npc.criticalPower ?: 0.0,
            armor = npc.armor ?: 0.0
        )
    }
}