package com.game.dungeonborn.dto.user


data class UserLoginResponseDTO(
    val login: String,
    val authorization: JwtPairDTO
) {

}