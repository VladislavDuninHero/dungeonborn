package com.game.dungeonborn.dto.user

data class UserRegistrationResponseDTO(
    val userId: Long,
    val login: String,
    val password: String,
    val email: String
)