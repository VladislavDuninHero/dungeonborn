package com.game.dungeonborn.entity.dungeon

import com.game.dungeonborn.entity.character.Character
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "dungeon_progress")
class DungeonProgress(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) {
    @ManyToOne(fetch = FetchType.LAZY)
    var dungeon: Dungeon? = null

    @ManyToOne(fetch = FetchType.LAZY)
    var dungeonLevel: DungeonLevel? = null

    @ManyToOne(fetch = FetchType.LAZY)
    var character: Character? = null

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    var createdAt: LocalDateTime? = null;

    @Column(name = "updated_at")
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null;
}