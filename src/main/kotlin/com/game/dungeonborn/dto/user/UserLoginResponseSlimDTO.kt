package com.game.dungeonborn.dto.user

data class UserLoginResponseSlimDTO(
    val userId: Long,
    val token: String,
) {
}