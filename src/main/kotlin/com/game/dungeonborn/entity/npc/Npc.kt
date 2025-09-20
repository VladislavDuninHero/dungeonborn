package com.game.dungeonborn.entity.npc

import com.game.dungeonborn.entity.dungeon.DungeonLevel
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "npc")
class Npc(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "name")
    var name: String? = "LichKing",

    @Column(name = "lvl")
    var lvl: Int? = 1,

    @Column(name = "is_friendly")
    var isFriendly: Boolean? = false,

    @Column(name = "is_boss")
    var isBoss: Boolean? = false,

    @Column(name = "spawn_chance")
    var spawnChance: Double? = 0.0,

    @Column(name = "max_hp")
    var maxHp: Double? = 0.0,

    @Column(name = "max_mana")
    var maxMana: Double? = 0.0,

    @Column(name = "damage_min")
    var minDamage: Double? = 0.0,

    @Column(name = "damage_max")
    var maxDamage: Double? = 0.0,

    @Column(name = "critical_chance")
    var criticalChance: Double? = 0.0,

    @Column(name = "critical_power")
    var criticalPower: Double? = 0.0,

    @Column(name = "armor")
    var armor: Double? = 0.0,
) {
    @ManyToMany
    @JoinTable(
        name = "npc_dungeon_level",
        joinColumns = [JoinColumn(name = "npc_id")],
        inverseJoinColumns = [JoinColumn(name = "dungeon_level_id")]
    )
    var dungeonLevels: MutableList<DungeonLevel> = mutableListOf()

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    var createdAt: LocalDateTime? = null;

    @Column(name = "updated_at")
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null;
}