package com.game.dungeonborn.dto.admin

import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull

data class AdminLoginDTO(
    @field:NotBlank
    @field:NotNull
    val login: String?,

    @field:NotBlank
    @field:NotNull
    val password: String?,
)
