package com.game.dungeonborn.entity.character

import com.game.dungeonborn.entity.item.Item
import jakarta.persistence.*

@Entity
@Table(name = "character_inventory_item")
class CharacterInventoryItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    var inventory: CharacterInventory? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item: Item? = null
}