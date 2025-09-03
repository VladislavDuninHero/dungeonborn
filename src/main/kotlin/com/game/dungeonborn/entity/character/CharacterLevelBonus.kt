package com.game.dungeonborn.entity.character

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "character_level_bonus")
class CharacterLevelBonus(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "level_number")
    var levelNumber: Int? = 0,

    @Column(name = "total_points")
    var totalPoints: Double? = 0.0,

    @Column(name = "bonus_hp")
    var bonusHp: Double? = 0.0,

    @Column(name = "bonus_strength")
    var bonusStrength: Double? = 0.0,

    @Column(name = "bonus_intellect")
    var bonusIntellect: Double? = 0.0,

    @Column(name = "bonus_agility")
    var bonusAgility: Double? = 0.0,
) {
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    var createdAt: LocalDateTime? = null;

    @Column(name = "updated_at")
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null;
}