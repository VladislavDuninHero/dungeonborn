package com.game.dungeonborn.controllers.character

import com.game.dungeonborn.constant.Message
import com.game.dungeonborn.constant.Route
import com.game.dungeonborn.dto.character.*
import com.game.dungeonborn.dto.official.SuccessMessageDTO
import com.game.dungeonborn.service.character.CharacterService
import jakarta.validation.constraints.NotNull
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

    @PutMapping(Route.API_UPDATE_CHARACTER_ROUTE)
    @PreAuthorize("hasAuthority('UPDATE_CHAR')")
    fun updateCharacter(
        @RequestBody @Validated updateCharacterDTO: UpdateCharacterDTO
    ): ResponseEntity<CharacterDTO> {
        val updatedCharacter = characterService.updateCharacter(updateCharacterDTO);

        return ResponseEntity.ok(updatedCharacter);
    }

    @DeleteMapping(Route.API_DELETE_ROUTE)
    @PreAuthorize("hasAuthority('DELETE_CHAR')")
    fun deleteCharacter(
        @PathVariable @NotNull id: Long
    ): ResponseEntity<SuccessMessageDTO> {
        characterService.deleteCharacter(id);

        return ResponseEntity.ok(SuccessMessageDTO(Message.DEFAULT_SUCCESS_MESSAGE));
    }

    @PostMapping(Route.API_SELECT_ROUTE)
    @PreAuthorize("hasAuthority('READ_CHAR')")
    fun selectCharacter(
        @RequestBody selectCharacterDTO: SelectCharacterDTO,
    ) : ResponseEntity<SelectCharacterResponseDTO> {
        val selectedCharacterResponse = characterService.selectCharacter(selectCharacterDTO);

        return ResponseEntity.ok(selectedCharacterResponse);
    }
}