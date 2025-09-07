package com.game.dungeonborn.entity.character

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.entity.item.Item
import com.game.dungeonborn.exception.character.InventoryIsFullException
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "character_inventory")
class CharacterInventory(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

) {
    companion object {
        const val MAX_INVENTORY_SIZE = 20
    }

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "inventory_item",
        joinColumns = [JoinColumn(name = "inventory_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")],
    )
    var items: MutableList<Item> = mutableListOf()
        set(value) {
            if (value.size > MAX_INVENTORY_SIZE) {
                throw InventoryIsFullException(ExceptionMessage.INVENTORY_IS_FULL)
            }
            field = value
        }

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    var createdAt: LocalDateTime? = null;

    @Column(name = "updated_at")
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null;
}