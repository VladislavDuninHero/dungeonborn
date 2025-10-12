package com.game.dungeonborn.dto.user


data class UserLoginResponseDTO(
    val userId: Long,
    val authorization: JwtPairDTO
) {

}