package com.game.dungeonborn.dto.user

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class UpdateUserDTO(
    @field:NotNull
    @field:NotBlank
    @field:Pattern(regexp = "^[a-zA-Z0-9]+$")
    val email: String,
)
