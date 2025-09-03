package com.game.dungeonborn.entity.character

import com.game.dungeonborn.enums.character.CharacterClass
import com.game.dungeonborn.enums.character.CharacterClassType
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "character_classes")
class CharacterClass(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    var name: CharacterClass,

    @Column(name = "class_type", nullable = false)
    @Enumerated(EnumType.STRING)
    var classType: CharacterClassType,

    @Column(name = "base_hp", nullable = false)
    var baseHp: Double = 0.0,

    @Column(name = "base_strength", nullable = false)
    var baseStrength: Double = 0.0,

    @Column(name = "base_intellect", nullable = false)
    var baseIntellect: Double = 0.0,

    @Column(name = "base_agility", nullable = false)
    var baseAgility: Double = 0.0,

    @Column(name = "base_critical_chance", nullable = false)
    var baseCriticalChance: Double = 0.0,

) {
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    var createdAt: LocalDateTime? = null;

    @Column(name = "updated_at")
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null;
}