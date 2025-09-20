package com.game.dungeonborn.entity.loot

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "loot_table")
class LootTable(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "name")
    var name: String,

    @Column(name = "description")
    var description: String,
) {
    @OneToMany(fetch = FetchType.LAZY, cascade = [(CascadeType.ALL)], mappedBy = "lootTable")
    var lootTableItems: MutableList<LootTableItems> = mutableListOf();

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    var createdAt: LocalDateTime? = null;

    @Column(name = "updated_at")
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null;
}