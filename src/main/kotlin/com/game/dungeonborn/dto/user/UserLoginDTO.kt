package com.game.dungeonborn.dto.user

import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull

data class UserLoginDTO(
    @field:NotBlank
    @field:NotNull
    val login: String?,

    @field:NotBlank
    @field:NotNull
    val password: String?,

    @field:NotBlank
    @field:NotNull
    val email: String?
)
