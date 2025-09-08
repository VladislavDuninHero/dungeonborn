package com.game.dungeonborn.controllers.character

import com.game.dungeonborn.constant.Route
import com.game.dungeonborn.dto.character.CharacterDTO
import com.game.dungeonborn.dto.character.CreateCharacterDTO
import com.game.dungeonborn.dto.character.CreateCharacterResponseDTO
import com.game.dungeonborn.service.character.CharacterService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

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

    @GetMapping(Route.API_GET_ROUTE)
    @PreAuthorize("hasAuthority('READ_CHAR')")
    fun getCharacterById(
        @PathVariable id: Long
    ): ResponseEntity<CharacterDTO> {
        val foundedCharacter = characterService.getCharacterById(id);

        return ResponseEntity.ok(foundedCharacter);
    }

}