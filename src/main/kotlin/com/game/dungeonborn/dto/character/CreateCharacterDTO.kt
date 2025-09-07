package com.game.dungeonborn.dto.character

import com.game.dungeonborn.enums.character.CharacterClass
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CreateCharacterDTO(

    @field:NotBlank
    @field:NotNull
    @field:Size(max = 20)
    val name: String,

    @field:NotNull
    val characterClass: CharacterClass,

    @field:NotNull
    val userId: Long
)
