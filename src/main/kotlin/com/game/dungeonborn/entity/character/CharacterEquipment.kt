package com.game.dungeonborn.entity.character

import com.game.dungeonborn.entity.item.Item
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "character_equipment")
class CharacterEquipment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head_item_id")
    var headItem: Item? = null

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shoulders_item_id")
    var shouldersItem: Item? = null

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chest_item_id")
    var chestItem: Item? = null

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hands_item_id")
    var handsItem: Item? = null

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legs_item_id")
    var legsItem: Item? = null

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feet_item_id")
    var feetItem: Item? = null

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weapon_item_id")
    var weaponItem: Item? = null

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    var createdAt: LocalDateTime? = null;

    @Column(name = "updated_at")
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null;
}