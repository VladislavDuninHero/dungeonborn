package com.game.dungeonborn.entity.item

import com.game.dungeonborn.enums.item.ItemQuality
import com.game.dungeonborn.enums.item.ItemType
import com.game.dungeonborn.enums.item.SlotType
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "items")
class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    var type: ItemType,

    @Column(name = "slot_type", nullable = false)
    @Enumerated(EnumType.STRING)
    var slotType: SlotType,

    @Column(name = "item_level", nullable = false)
    var itemLevel: Int,

    @Column(name = "quality", nullable = false)
    @Enumerated(EnumType.STRING)
    var quality: ItemQuality,

    @Column(name = "min_dungeon_level", nullable = false)
    var minDungeonLevel: Int,

    @Column(name = "stamina", nullable = false)
    var stamina: Double,

    @Column(name = "strength", nullable = false)
    var strength: Double,

    @Column(name = "intellect", nullable = false)
    var intellect: Double,

    @Column(name = "agility", nullable = false)
    var agility: Double,

    @Column(name = "critical_chance", nullable = false)
    var criticalChance: Double,

    @Column(name = "critical_power", nullable = false)
    var criticalPower: Double,

    @Column(name = "armor", nullable = false)
    var armor: Double,

    @Column(name = "is_available", nullable = false)
    var isAvailable: Boolean,

    @Column(name = "drop_chance", nullable = false)
    var dropChance: Double,
) {
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    var createdAt: LocalDateTime? = null;

    @Column(name = "updated_at")
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null;
}