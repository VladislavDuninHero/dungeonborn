package com.game.dungeonborn.repositories

import com.game.dungeonborn.entity.npc.Npc
import org.springframework.data.jpa.repository.JpaRepository

interface NpcRepository : JpaRepository<Npc, Long> {
}