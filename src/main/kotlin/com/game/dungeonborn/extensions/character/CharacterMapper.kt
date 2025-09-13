package com.game.dungeonborn.extensions.character

import com.game.dungeonborn.dto.character.CharacterDTO
import com.game.dungeonborn.dto.character.CharacterSlimDTO
import com.game.dungeonborn.dto.item.ItemDTO
import com.game.dungeonborn.entity.character.Character
import com.game.dungeonborn.exception.RequiredFieldException
import com.game.dungeonborn.extensions.stats.CharacterStatsMapper
import com.game.dungeonborn.service.stat.CharacterStatsUtils
import com.game.dungeonborn.service.utils.character.CharacterUtils
import org.springframework.stereotype.Component

@Component
class CharacterMapper(
    private val characterUtils: CharacterUtils,
    private val characterStatsUtils: CharacterStatsUtils,
    private val characterStatsMapper: CharacterStatsMapper
) {
    fun toCharacterDTO(character: Character) : CharacterDTO {
        val characterId = character.id ?: throw RequiredFieldException("Character id is required");

        val mappedEquipment = characterUtils.convertCharacterEquipmentToList(character.characterEquipment)
            .filterNotNull().map { ItemDTO(it.name) }
        val characterClass = character.characterClass?.name;
        val characterStats = characterStatsUtils.findCharacterStatsByCharacterIdAndGet(characterId);
        val mappedInventory = character.characterInventory?.items?.map {
            ItemDTO(it.name);
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

    fun toCharacterSlimDTO(character: Character) : CharacterSlimDTO {
        val characterClass = character.characterClass?.name;

        return CharacterSlimDTO(
            character.id ?: 0,
            character.name,
            character.characterLevel,
            characterClass,
        );
    }
}