package com.game.dungeonborn.service.character

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.dto.character.*
import com.game.dungeonborn.dto.character.equipment.*
import com.game.dungeonborn.dto.character.inventory.*
import com.game.dungeonborn.dto.item.ItemDTO
import com.game.dungeonborn.entity.character.*
import com.game.dungeonborn.entity.item.Item
import com.game.dungeonborn.enums.item.ItemQuality
import com.game.dungeonborn.enums.item.ItemType
import com.game.dungeonborn.enums.item.SlotType
import com.game.dungeonborn.exception.RequiredFieldException
import com.game.dungeonborn.exception.items.ItemNotFoundException
import com.game.dungeonborn.extensions.character.CharacterMapper
import com.game.dungeonborn.extensions.stats.CharacterStatsMapper
import com.game.dungeonborn.repositories.CharacterClassesRepository
import com.game.dungeonborn.repositories.CharacterInventoryRepository
import com.game.dungeonborn.repositories.CharacterRepository
import com.game.dungeonborn.repositories.CharacterStatsRepository
import com.game.dungeonborn.service.dungeon.DungeonUtils
import com.game.dungeonborn.service.stat.CharacterStatsUtils
import com.game.dungeonborn.service.utils.character.CharacterUtils
import com.game.dungeonborn.service.utils.inventory.CharacterInventoryUtils
import com.game.dungeonborn.service.utils.item.ItemsUtils
import com.game.dungeonborn.service.utils.user.UserUtils
import com.game.dungeonborn.service.validation.character.UpdateCharacterValidationManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CharacterService(
    private val userUtils: UserUtils,
    private val characterRepository: CharacterRepository,
    private val characterStatsRepository: CharacterStatsRepository,
    private val characterClassesRepository: CharacterClassesRepository,
    private val characterUtils: CharacterUtils,
    private val characterMapper: CharacterMapper,
    private val updateCharacterValidationManager: UpdateCharacterValidationManager,
    private val characterStatsUtils: CharacterStatsUtils,
    private val characterStatsMapper: CharacterStatsMapper,
    private val dungeonUtils: DungeonUtils,
    private val itemsUtils: ItemsUtils,
    private val characterInventoryUtils: CharacterInventoryUtils,
    private val characterInventoryRepository: CharacterInventoryRepository
) {

    @Transactional
    fun createCharacter(character: CreateCharacterDTO): CreateCharacterResponseDTO {
        characterUtils.validateCharacterName(character.name);

        val user = userUtils.findUserByIdAndGet(character.userId);
        val characterClassName = character.characterClass;

        val characterClass = characterClassesRepository.findByClassName(characterClassName);

        val baseIntellect = characterClass?.baseIntellect ?: 0.0;
        val baseStrength = characterClass?.baseStrength ?: 0.0;
        val baseAgility = characterClass?.baseAgility ?: 0.0;
        val baseHp = characterClass?.baseHp ?: 0.0;
        val baseCriticalChance = characterClass?.baseCriticalChance ?: 0.0;
        val mainStat = characterClass?.mainStat ?: throw RequiredFieldException("Main stat field is required");

        val calculatedMana = characterUtils.calculateCharacterMana(baseIntellect, 1);
        val calculatedDamage = characterUtils.getBaseDamageForCharacterClassMainStat(characterClass) * characterUtils
                .getSecondStatCoefficient(mainStat);

        val characterStats = CharacterStats(
            totalHp = baseHp,
            totalMana = calculatedMana,
            totalStrength = baseStrength,
            totalIntellect = baseIntellect,
            totalAgility = baseAgility,
            totalCriticalChance = baseCriticalChance,
            totalGearScore = 0.0,
            totalDamage = calculatedDamage,
            totalArmor = 0.0,
        );

        val characterInventory = CharacterInventory();

        val newCharacter = Character(
            name = character.name,
            characterLevel = 1
        );

        newCharacter.characterClass = characterClass;
        newCharacter.characterStat = characterStats;
        newCharacter.characterInventory = characterInventory;
        newCharacter.characterEquipment = CharacterEquipment();
        newCharacter.user = user;

        val createdCharacter = characterRepository.save(newCharacter);

        return CreateCharacterResponseDTO(
            createdCharacter.id ?: 0,
            user.id ?: 0,
            createdCharacter.name,
            1
        )
    }

    fun getCharacterById(id: Long): CharacterDTO {
        val character = characterUtils.findCharacterById(id);

        val mappedEquipment = characterUtils.convertCharacterEquipmentToList(character.characterEquipment)
            .filterNotNull().map {
                ItemDTO(
                    it.id ?: 0,
                    it.name,
                    it.type,
                    it.slotType,
                    it.itemLevel,
                    it.quality,
                    it.stamina,
                    it.strength,
                    it.intellect,
                    it.agility,
                    it.criticalChance,
                    it.criticalPower,
                    it.armor
                )
            }
        val characterClass = character.characterClass?.name;
        val characterStats = characterStatsUtils.findCharacterStatsByCharacterIdAndGet(id);
        val mappedInventory = character.characterInventory?.items?.map {
            InventoryItemDTO(
                it.id ?: 0,
                ItemDTO(
                    it.item?.id ?: 0,
                    it.item?.name ?: "Unknown",
                    it.item?.type ?: ItemType.UNKNOWN,
                    it.item?.slotType ?: SlotType.UNKNOWN,
                    it.item?.itemLevel ?: 0,
                    it.item?.quality ?: ItemQuality.UNKNOWN,
                    it.item?.stamina ?: 0.0,
                    it.item?.strength ?: 0.0,
                    it.item?.intellect ?: 0.0,
                    it.item?.agility ?: 0.0,
                    it.item?.criticalChance ?: 0.0,
                    it.item?.criticalPower ?: 0.0,
                    it.item?.armor ?: 0.0,
                )
            )
        }.orEmpty();


        return CharacterDTO(
            character.id ?: 0,
            character.name,
            character.characterLevel,
            characterClass,
            characterStatsMapper.toDTO(characterStats),
            mappedEquipment,
            mappedInventory
        );
    }

    fun getAllCharactersForUserId(userId: Long): List<CharacterDTO> {
        val characters = characterUtils.findAllCharactersByUserId(userId);

        return characters.map{
            characterMapper.toCharacterDTO(it)
        }
    }

    @Transactional
    fun updateCharacter(updateCharacterDTO: UpdateCharacterDTO): CharacterDTO {
        updateCharacterValidationManager.validate(updateCharacterDTO);

        val character = characterUtils.findCharacterById(updateCharacterDTO.characterId);

        character.name = updateCharacterDTO.name;

        val updatedCharacter = characterRepository.save(character);

        return characterMapper.toCharacterDTO(updatedCharacter);
    }

    @Transactional
    fun deleteCharacter(id: Long) {
        val character = characterUtils.findCharacterById(id);

        characterRepository.delete(character);
    }

    fun selectCharacter(selectCharacterDTO: SelectCharacterDTO) : SelectCharacterResponseDTO {
        val character = characterMapper.toCharacterDTO(characterUtils.findCharacterById(selectCharacterDTO.characterId));

        val availableDungeons = dungeonUtils.getAvailableDungeonsForCharacter(selectCharacterDTO.characterId);

        return SelectCharacterResponseDTO(
            character,
            availableDungeons
        )
    }

    @Transactional
    fun addToInventory(addToInventoryDTO: AddToInventoryDTO): AddToInventoryResponseDTO {
        val foundCharacter = characterUtils.findCharacterById(addToInventoryDTO.characterId)
        val foundItem = itemsUtils.findItemByIdAndGet(addToInventoryDTO.itemId)
        val inventory = foundCharacter.characterInventory;
        val inventoryItems = inventory?.items ?: mutableListOf();

        val newInventoryItem = CharacterInventoryItem();
        newInventoryItem.item = foundItem;
        newInventoryItem.inventory = inventory;

        characterInventoryUtils.addItemToInventory(inventoryItems, newInventoryItem);

        val savedCharacter = characterRepository.save(foundCharacter);

        val updatedInventoryItems = savedCharacter.characterInventory?.items ?: emptyList();

        return AddToInventoryResponseDTO(
            addToInventoryDTO.characterId,
            InventoryDTO(
                inventory?.id,
                updatedInventoryItems.map {
                    InventoryItemDTO(
                        it.id ?: 0,
                        ItemDTO(
                            it.item?.id ?: 0,
                            it.item?.name ?: "Unknown",
                            it.item?.type ?: ItemType.UNKNOWN,
                            it.item?.slotType ?: SlotType.UNKNOWN,
                            it.item?.itemLevel ?: 0,
                            it.item?.quality ?: ItemQuality.UNKNOWN,
                            it.item?.stamina ?: 0.0,
                            it.item?.strength ?: 0.0,
                            it.item?.intellect ?: 0.0,
                            it.item?.agility ?: 0.0,
                            it.item?.criticalChance ?: 0.0,
                            it.item?.criticalPower ?: 0.0,
                            it.item?.armor ?: 0.0,
                        )
                    )
                }
            )
        )
    }

    @Transactional
    fun deleteFromInventory(deleteFromInventoryDTO: DeleteFromInventoryDTO) : DeleteFromInventoryResponseDTO {
        val foundInventory = characterInventoryUtils.getCharacterInventoryByIdAndGet(deleteFromInventoryDTO.inventoryId);
        val foundItem = foundInventory.items
            .find {
                it.id == deleteFromInventoryDTO.itemId
            } ?: throw ItemNotFoundException(ExceptionMessage.ITEM_NOT_FOUND);

        foundInventory.items.remove(foundItem);

        val updatedInventory = characterInventoryRepository.save(foundInventory);

        return DeleteFromInventoryResponseDTO(
            updatedInventory.id ?: 0,
            updatedInventory.items.map {
                InventoryItemDTO(
                    it.id ?: 0,
                    ItemDTO(
                        it.item?.id ?: 0,
                        it.item?.name ?: "Unknown",
                        it.item?.type ?: ItemType.UNKNOWN,
                        it.item?.slotType ?: SlotType.UNKNOWN,
                        it.item?.itemLevel ?: 0,
                        it.item?.quality ?: ItemQuality.UNKNOWN,
                        it.item?.stamina ?: 0.0,
                        it.item?.strength ?: 0.0,
                        it.item?.intellect ?: 0.0,
                        it.item?.agility ?: 0.0,
                        it.item?.criticalChance ?: 0.0,
                        it.item?.criticalPower ?: 0.0,
                        it.item?.armor ?: 0.0,
                    )
                )
            }
        )
    }

    @Transactional
    fun clearInventory(id: Long) : DeleteFromInventoryResponseDTO {
        val foundInventory = characterInventoryUtils.getCharacterInventoryByIdAndGet(id);

        foundInventory.items.clear();

        val updatedInventory = characterInventoryRepository.save(foundInventory);

        return DeleteFromInventoryResponseDTO(
            updatedInventory.id ?: 0,
            updatedInventory.items.map {
                InventoryItemDTO(
                    it.id ?: 0,
                    ItemDTO(
                        it.item?.id ?: 0,
                        it.item?.name ?: "Unknown",
                        it.item?.type ?: ItemType.UNKNOWN,
                        it.item?.slotType ?: SlotType.UNKNOWN,
                        it.item?.itemLevel ?: 0,
                        it.item?.quality ?: ItemQuality.UNKNOWN,
                        it.item?.stamina ?: 0.0,
                        it.item?.strength ?: 0.0,
                        it.item?.intellect ?: 0.0,
                        it.item?.agility ?: 0.0,
                        it.item?.criticalChance ?: 0.0,
                        it.item?.criticalPower ?: 0.0,
                        it.item?.armor ?: 0.0,
                    )
                )
            }
        )
    }

    @Transactional
    fun addToEquipment(addToEquipmentDTO: AddToEquipmentDTO) : AddToEquipmentResponseDTO {
        val foundCharacter = characterUtils.findCharacterById(addToEquipmentDTO.characterId);
        val inventory = foundCharacter.characterInventory ?: throw RequiredFieldException("Inventory is required");
        val equipment = foundCharacter.characterEquipment ?: throw RequiredFieldException("Equipment is required");

        val foundItem = inventory.items.find { it.id == addToEquipmentDTO.inventoryItemId }
            ?: throw ItemNotFoundException(ExceptionMessage.ITEM_NOT_FOUND);

        var currentEquipmentItem: Item ?= null;

        when(foundItem.item?.slotType) {
            SlotType.HEAD -> {
                currentEquipmentItem = equipment.headItem;
                equipment.headItem = foundItem.item
            };
            SlotType.SHOULDERS -> {
                currentEquipmentItem = equipment.shouldersItem;
                equipment.shouldersItem = foundItem.item
            };
            SlotType.CHEST -> {
                currentEquipmentItem = equipment.chestItem;
                equipment.chestItem = foundItem.item
            };
            SlotType.HANDS -> {
                currentEquipmentItem = equipment.handsItem;
                equipment.handsItem = foundItem.item
            };
            SlotType.LEGS -> {
                currentEquipmentItem = equipment.legsItem;
                equipment.legsItem = foundItem.item
            };
            SlotType.FEET -> {
                currentEquipmentItem = equipment.feetItem;
                equipment.feetItem = foundItem.item
            };
            SlotType.WEAPON -> {
                currentEquipmentItem = equipment.weaponItem;
                equipment.weaponItem = foundItem.item
            };
            else -> throw RequiredFieldException("Slot type is required");
        }

        val findItemToRemoveFromInventory = inventory.items.find { it.id == addToEquipmentDTO.inventoryItemId };
        inventory.items.remove(findItemToRemoveFromInventory);

        if (currentEquipmentItem != null) {
            val newInventoryItem = CharacterInventoryItem();
            newInventoryItem.id = findItemToRemoveFromInventory?.id;
            newInventoryItem.item = currentEquipmentItem;
            newInventoryItem.inventory = inventory;

            inventory.items.add(newInventoryItem);
        }

        val updatedCharacter = characterRepository.save(foundCharacter);

        return AddToEquipmentResponseDTO(
            EquipmentDTO(
                equipment.id ?: 0,
                updatedCharacter.characterEquipment?.headItem?.id ?: 0,
                updatedCharacter.characterEquipment?.shouldersItem?.id ?: 0,
                updatedCharacter.characterEquipment?.chestItem?.id ?: 0,
                updatedCharacter.characterEquipment?.handsItem?.id ?: 0,
                updatedCharacter.characterEquipment?.legsItem?.id ?: 0,
                updatedCharacter.characterEquipment?.feetItem?.id ?: 0,
                updatedCharacter.characterEquipment?.weaponItem?.id ?: 0,
            )
        );
    }

    @Transactional
    fun deleteFromEquipment(deleteFromEquipmentDTO: DeleteFromEquipmentDTO) : DeleteFromEquipmentResponseDTO {
        val foundCharacter = characterUtils.findCharacterById(deleteFromEquipmentDTO.characterId);
        val equipment = foundCharacter.characterEquipment ?: throw RequiredFieldException("Equipment is required");


        when(deleteFromEquipmentDTO.slotType) {
            SlotType.HEAD -> equipment.headItem = null;
            SlotType.SHOULDERS -> equipment.shouldersItem = null;
            SlotType.CHEST -> equipment.chestItem = null;
            SlotType.HANDS -> equipment.handsItem = null;
            SlotType.LEGS -> equipment.legsItem = null;
            SlotType.FEET -> equipment.feetItem = null;
            SlotType.WEAPON -> equipment.weaponItem = null;
            else -> throw RequiredFieldException("Slot type is required");
        }

        val updatedCharacter = characterRepository.save(foundCharacter);

        return DeleteFromEquipmentResponseDTO(
            EquipmentDTO(
                equipment.id ?: 0,
                updatedCharacter.characterEquipment?.headItem?.id ?: 0,
                updatedCharacter.characterEquipment?.shouldersItem?.id ?: 0,
                updatedCharacter.characterEquipment?.chestItem?.id ?: 0,
                updatedCharacter.characterEquipment?.handsItem?.id ?: 0,
                updatedCharacter.characterEquipment?.legsItem?.id ?: 0,
                updatedCharacter.characterEquipment?.feetItem?.id ?: 0,
                updatedCharacter.characterEquipment?.weaponItem?.id ?: 0,
            )
        );
    }

    @Transactional
    fun deleteFromEquipmentAndMoveToInventory (
        deleteFromEquipmentDTO: DeleteFromEquipmentDTO
    ): DeleteFromEquipmentAndMoveToInventoryResponseDTO {
        val foundCharacter = characterUtils.findCharacterById(deleteFromEquipmentDTO.characterId);
        val equipment = foundCharacter.characterEquipment ?: throw RequiredFieldException("Equipment is required");
        val inventory = foundCharacter.characterInventory;
        val inventoryItems = inventory?.items ?: mutableListOf();

        val foundItemInEquipment = when(deleteFromEquipmentDTO.slotType) {
            SlotType.HEAD -> equipment.headItem;
            SlotType.SHOULDERS -> equipment.shouldersItem;
            SlotType.CHEST -> equipment.chestItem;
            SlotType.HANDS -> equipment.handsItem;
            SlotType.LEGS -> equipment.legsItem;
            SlotType.FEET -> equipment.feetItem;
            SlotType.WEAPON -> equipment.weaponItem;
            else -> throw RequiredFieldException("Slot type is required");
        }

        if (foundItemInEquipment == null) {
            throw ItemNotFoundException(ExceptionMessage.ITEM_NOT_FOUND);
        }

        val newInventoryItem = CharacterInventoryItem();
        newInventoryItem.item = foundItemInEquipment;
        newInventoryItem.inventory = inventory;

        characterInventoryUtils.addItemToInventory(inventoryItems, newInventoryItem);

        when(deleteFromEquipmentDTO.slotType) {
            SlotType.HEAD -> equipment.headItem = null;
            SlotType.SHOULDERS -> equipment.shouldersItem = null;
            SlotType.CHEST -> equipment.chestItem = null;
            SlotType.HANDS -> equipment.handsItem = null;
            SlotType.LEGS -> equipment.legsItem = null;
            SlotType.FEET -> equipment.feetItem = null;
            SlotType.WEAPON -> equipment.weaponItem = null;
            else -> throw RequiredFieldException("Slot type is required");
        }

        val updatedCharacter = characterRepository.save(foundCharacter);

        return DeleteFromEquipmentAndMoveToInventoryResponseDTO(
            EquipmentDTO(
                equipment.id ?: 0,
                updatedCharacter.characterEquipment?.headItem?.id ?: 0,
                updatedCharacter.characterEquipment?.shouldersItem?.id ?: 0,
                updatedCharacter.characterEquipment?.chestItem?.id ?: 0,
                updatedCharacter.characterEquipment?.handsItem?.id ?: 0,
                updatedCharacter.characterEquipment?.legsItem?.id ?: 0,
                updatedCharacter.characterEquipment?.feetItem?.id ?: 0,
                updatedCharacter.characterEquipment?.weaponItem?.id ?: 0,
            ),
            InventoryDTO(
                inventory?.id,
                inventory?.items?.map {
                    InventoryItemDTO(
                        it.id ?: 0,
                        ItemDTO(
                            it.item?.id ?: 0,
                            it.item?.name ?: "Unknown",
                            it.item?.type ?: ItemType.UNKNOWN,
                            it.item?.slotType ?: SlotType.UNKNOWN,
                            it.item?.itemLevel ?: 0,
                            it.item?.quality ?: ItemQuality.UNKNOWN,
                            it.item?.stamina ?: 0.0,
                            it.item?.strength ?: 0.0,
                            it.item?.intellect ?: 0.0,
                            it.item?.agility ?: 0.0,
                            it.item?.criticalChance ?: 0.0,
                            it.item?.criticalPower ?: 0.0,
                            it.item?.armor ?: 0.0,
                        )
                    )
                } ?: emptyList()
            )
        );
    }
}