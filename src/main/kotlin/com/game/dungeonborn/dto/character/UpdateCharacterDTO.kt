package com.game.dungeonborn.dto.character

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UpdateCharacterDTO(

    @field:NotNull
    val characterId: Long,

    @field:NotNull
    @field:NotBlank
    @field:Size(min = 2, max = 20)
    @field:Pattern(regexp = "^[a-zA-Z]+$")
    val name: String
)
