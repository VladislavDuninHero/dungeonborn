package com.game.dungeonborn.entity.character

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "character_stats")
class CharacterStats(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "total_hp", nullable = false)
    var totalHp: Double? = 0.0,

    @Column(name = "total_mana", nullable = false)
    var totalMana: Double? = 0.0,

    @Column(name = "total_strength", nullable = false)
    var totalStrength: Double? = 0.0,

    @Column(name = "total_intellect", nullable = false)
    var totalIntellect: Double? = 0.0,

    @Column(name = "total_agility", nullable = false)
    var totalAgility: Double? = 0.0,

    @Column(name = "total_critical_chance", nullable = false)
    var totalCriticalChance: Double? = 0.0,

    @Column(name = "total_critical_power", nullable = false)
    var totalCriticalPower: Double? = 1.0,

    @Column(name = "total_gear_score", nullable = false)
    var totalGearScore: Double? = 0.0,

    @Column(name = "total_damage", nullable = false)
    var totalDamage: Double? = 0.0,

    @Column(name = "total_armor", nullable = false)
    var totalArmor: Double? = 0.0,
) {
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    var createdAt: LocalDateTime? = null;

    @Column(name = "updated_at")
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null;
}