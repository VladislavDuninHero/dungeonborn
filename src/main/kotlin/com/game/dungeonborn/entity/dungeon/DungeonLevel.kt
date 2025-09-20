package com.game.dungeonborn.entity.dungeon

import com.game.dungeonborn.entity.npc.Npc
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "dungeon_level")
class DungeonLevel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "recommended_power")
    var recommendedPower: Double,

    @Column(name = "lvl")
    var lvl: Int,

    @Column(name = "total_enemies_npc")
    var totalEnemiesNpc: Int,

    @Column(name = "rating_points")
    var ratingPoints: Double,

    @Column(name = "dungeon_rounds")
    var dungeonRounds: Int,
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dungeon_id")
    var dungeon: Dungeon? = null;

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "dungeon_loot_id")
    var dungeonLoot: DungeonLoot? = null;

    @ManyToMany(mappedBy = "dungeonLevels")
    var npcs: MutableList<Npc> = mutableListOf()

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    var createdAt: LocalDateTime? = null;

    @Column(name = "updated_at")
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null;
}