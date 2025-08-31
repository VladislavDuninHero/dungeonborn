package com.game.dungeonborn.dto.user

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.jetbrains.annotations.NotNull


data class UserRegistrationDTO(
    @field:NotBlank
    @field:NotNull
    @field:Pattern(regexp = "^[a-zA-Z0-9]+$")
    val login: String?,

    @field:NotBlank
    @field:NotNull
    @field:Pattern(regexp = "^[a-zA-Z0-9]+$")
    val password: String?,

    @field:NotBlank
    @field:NotNull
    val email: String?
)