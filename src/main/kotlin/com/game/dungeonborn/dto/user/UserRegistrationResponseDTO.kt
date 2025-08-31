package com.game.dungeonborn.dto.user

data class UserRegistrationResponseDTO(
    val id: Long,
    val login: String,
    val email: String
)