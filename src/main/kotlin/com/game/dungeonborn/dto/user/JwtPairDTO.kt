package com.game.dungeonborn.dto.user

data class JwtPairDTO(
    val accessToken: String,
    val refreshToken: String
)