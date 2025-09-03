package com.game.dungeonborn.entity.character

import com.game.dungeonborn.entity.user.User
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "characters")
class Character(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String = "",

    @Column(name = "character_level", nullable = false)
    var characterLevel: Int = 0,

    @Column(name = "total_experience", nullable = false)
    var totalExperience: Double = 0.0,

    @Column(name = "is_bot", nullable = false)
    var isBot: Boolean = false,
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_class_id")
    var characterClass: CharacterClass? = null

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_stat_id")
    var characterStat: CharacterStats? = null

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    var createdAt: LocalDateTime? = null;

    @Column(name = "updated_at")
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null;
}