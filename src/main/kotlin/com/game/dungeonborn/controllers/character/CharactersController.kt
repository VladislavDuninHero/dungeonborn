package com.game.dungeonborn.controllers.character

import com.game.dungeonborn.constant.Route
import com.game.dungeonborn.dto.character.CreateCharacterDTO
import com.game.dungeonborn.dto.character.CreateCharacterResponseDTO
import com.game.dungeonborn.service.character.CharacterService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(Route.API_CHARACTERS_ROUTE)
class CharactersController(
    private val characterService: CharacterService
) {

    @PostMapping(Route.API_CREATE_ROUTE)
    @PreAuthorize("hasAuthority('CREATE_CHAR')")
    fun createCharacter(
        @RequestBody @Validated createCharacterDTO: CreateCharacterDTO
    ): ResponseEntity<CreateCharacterResponseDTO> {
        val createdCharacter = characterService.createCharacter(createCharacterDTO);

        return ResponseEntity.ok(createdCharacter);
    }
}