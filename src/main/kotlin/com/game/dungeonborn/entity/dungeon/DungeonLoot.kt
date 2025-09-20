package com.game.dungeonborn.entity.dungeon

import com.game.dungeonborn.entity.loot.LootTable
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "dungeon_loot")
class DungeonLoot(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "exp_reward")
    var expReward: Double? = 0.0,
) {
    @OneToOne(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY)
    var lootTable: LootTable? = null

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    var createdAt: LocalDateTime? = null;

    @Column(name = "updated_at")
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null;
}