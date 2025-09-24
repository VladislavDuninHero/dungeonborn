package com.game.dungeonborn.dto.character.equipment


data class EquipmentDTO(
    var equipmentId: Long,
    var headSlotItemId: Long,
    var shouldersSlotItemId: Long,
    var chestSlotItemId: Long,
    var handSlotItemId: Long,
    var legsSlotItemId: Long,
    var feetSlotItemId: Long,
    var weaponSlotItemId: Long,
)