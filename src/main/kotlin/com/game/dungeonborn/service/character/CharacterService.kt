package com.game.dungeonborn.service.character

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.dto.character.CharacterClassDTO
import com.game.dungeonborn.dto.character.CharacterDTO
import com.game.dungeonborn.dto.character.CreateCharacterDTO
import com.game.dungeonborn.dto.character.CreateCharacterResponseDTO
import com.game.dungeonborn.dto.item.ItemDTO
import com.game.dungeonborn.entity.character.Character
import com.game.dungeonborn.entity.character.CharacterEquipment
import com.game.dungeonborn.entity.character.CharacterInventory
import com.game.dungeonborn.entity.character.CharacterStats
import com.game.dungeonborn.exception.RequiredFieldException
import com.game.dungeonborn.extensions.character.CharacterMapper
import com.game.dungeonborn.repositories.CharacterClassesRepository
import com.game.dungeonborn.repositories.CharacterRepository
import com.game.dungeonborn.repositories.CharacterStatsRepository
import com.game.dungeonborn.service.utils.character.CharacterUtils
import com.game.dungeonborn.service.utils.user.UserUtils
import org.springframework.stereotype.Service

@Service
class CharacterService(
    private val userUtils: UserUtils,
    private val characterRepository: CharacterRepository,
    private val characterStatsRepository: CharacterStatsRepository,
    private val characterClassesRepository: CharacterClassesRepository,
    private val characterUtils: CharacterUtils,
    private val characterMapper: CharacterMapper
) {

    fun createCharacter(character: CreateCharacterDTO): CreateCharacterResponseDTO {
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
            .filterNotNull().map { ItemDTO(it.name) }
        val characterClass = character.characterClass?.name;

        return CharacterDTO(
            character.id ?: 0,
            character.name,
            character.characterLevel,
            characterClass,
            mappedEquipment
        );
    }

    fun getAllCharactersForUserId(userId: Long): List<CharacterDTO> {
        val characters = characterUtils.findAllCharactersByUserId(userId);

        return characters.map{
            characterMapper.toCharacterDTO(it)
        }
    }
}