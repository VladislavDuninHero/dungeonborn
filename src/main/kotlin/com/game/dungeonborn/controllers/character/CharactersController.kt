package com.game.dungeonborn.controllers.character

import com.game.dungeonborn.constant.Message
import com.game.dungeonborn.constant.Route
import com.game.dungeonborn.dto.character.*
import com.game.dungeonborn.dto.character.equipment.*
import com.game.dungeonborn.dto.character.inventory.AddToInventoryDTO
import com.game.dungeonborn.dto.character.inventory.AddToInventoryResponseDTO
import com.game.dungeonborn.dto.character.inventory.DeleteFromInventoryDTO
import com.game.dungeonborn.dto.character.inventory.DeleteFromInventoryResponseDTO
import com.game.dungeonborn.dto.character.levels.CharacterLevelPointsAddDTO
import com.game.dungeonborn.dto.character.levels.CharacterLevelPointsAddResponseDTO
import com.game.dungeonborn.dto.character.levels.CharacterLevelUpDTO
import com.game.dungeonborn.dto.character.levels.CharacterLevelUpResponseDTO
import com.game.dungeonborn.dto.official.SuccessMessageDTO
import com.game.dungeonborn.service.character.CharacterService
import com.game.dungeonborn.service.utils.level.CharacterLevelService
import jakarta.validation.constraints.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Route.API_CHARACTERS_ROUTE)
class CharactersController(
    private val characterService: CharacterService,
    private val characterLevelService: CharacterLevelService
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
        @RequestBody @Validated selectCharacterDTO: SelectCharacterDTO,
    ) : ResponseEntity<SelectCharacterResponseDTO> {
        val selectedCharacterResponse = characterService.selectCharacter(selectCharacterDTO);

        return ResponseEntity.ok(selectedCharacterResponse);
    }

    @PostMapping(Route.API_CHARACTER_INVENTORY_ADD)
    @PreAuthorize("hasAuthority('UPDATE_CHAR')")
    fun addToInventory(
        @RequestBody @Validated addToInventory: AddToInventoryDTO,
    ) : ResponseEntity<AddToInventoryResponseDTO> {
        val addedToInventory = characterService.addToInventory(addToInventory);

        return ResponseEntity.ok(addedToInventory);
    }

    @DeleteMapping(Route.API_CHARACTER_INVENTORY_DELETE)
    @PreAuthorize("hasAuthority('UPDATE_CHAR')")
    fun deleteFromInventory(
        @RequestBody @Validated deleteFromInventoryDTO: DeleteFromInventoryDTO,
    ) : ResponseEntity<DeleteFromInventoryResponseDTO> {
        val deletedFromInventory = characterService.deleteFromInventory(deleteFromInventoryDTO);

        return ResponseEntity.ok(deletedFromInventory);
    }

    @DeleteMapping(Route.API_CHARACTER_INVENTORY_CLEAR)
    @PreAuthorize("hasAuthority('UPDATE_CHAR')")
    fun clearInventory(
        @PathVariable @NotNull id: Long
    ) : ResponseEntity<DeleteFromInventoryResponseDTO> {
        val deletedFromInventory = characterService.clearInventory(id);

        return ResponseEntity.ok(deletedFromInventory);
    }

    @PostMapping(Route.API_CHARACTER_EQUIPMENT_ADD)
    @PreAuthorize("hasAuthority('UPDATE_CHAR')")
    fun addToEquipment(
        @RequestBody @Validated addToEquipmentDTO: AddToEquipmentDTO
    ) : ResponseEntity<AddToEquipmentResponseDTO> {
        val addedToEquipment = characterService.addToEquipment(addToEquipmentDTO);

        return ResponseEntity.ok(addedToEquipment);
    }

    @DeleteMapping(Route.API_CHARACTER_EQUIPMENT_DELETE)
    @PreAuthorize("hasAuthority('UPDATE_CHAR')")
    fun deleteFromEquipment(
        @RequestBody @Validated deleteFromEquipmentDTO: DeleteFromEquipmentDTO
    ) : ResponseEntity<DeleteFromEquipmentResponseDTO> {
        val deletedFromEquipment = characterService.deleteFromEquipment(deleteFromEquipmentDTO);

        return ResponseEntity.ok(deletedFromEquipment);
    }

    @PostMapping(Route.API_CHARACTER_EQUIPMENT_ITEM_MOVE_TO_INVENTORY)
    @PreAuthorize("hasAuthority('UPDATE_CHAR')")
    fun moveFromEquipmentToInventory(
        @RequestBody @Validated deleteFromEquipmentDTO: DeleteFromEquipmentDTO
    ) : ResponseEntity<DeleteFromEquipmentAndMoveToInventoryResponseDTO> {
        val deletedFromEquipment = characterService.deleteFromEquipmentAndMoveToInventory(deleteFromEquipmentDTO);

        return ResponseEntity.ok(deletedFromEquipment);
    }

    @PostMapping(Route.API_CHARACTER_LEVEL_UP)
    @PreAuthorize("hasAuthority('UPDATE_CHAR')")
    fun levelUp(
        @RequestBody @Validated characterLevelUpDTO: CharacterLevelUpDTO
    ) : ResponseEntity<CharacterLevelUpResponseDTO> {
        val uppedLevel = characterLevelService.levelUp(characterLevelUpDTO);

        return ResponseEntity.ok(uppedLevel);
    }

    @PostMapping(Route.API_CHARACTER_LEVEL_ADD_POINTS)
    @PreAuthorize("hasAuthority('UPDATE_CHAR')")
    fun addLevelPoints(
        @RequestBody @Validated addLevelPointsDTO: CharacterLevelPointsAddDTO,
    ) : ResponseEntity<CharacterLevelPointsAddResponseDTO> {
        val uppedLevel = characterLevelService.addCharacterLevelPoints(addLevelPointsDTO);

        return ResponseEntity.ok(uppedLevel);
    }
}