package com.game.dungeonborn.extensions.character

import com.game.dungeonborn.dto.character.CharacterDTO
import com.game.dungeonborn.dto.item.ItemDTO
import com.game.dungeonborn.entity.character.Character
import com.game.dungeonborn.service.utils.character.CharacterUtils
import org.springframework.stereotype.Component

@Component
class CharacterMapper(
    private val characterUtils: CharacterUtils,
) {
    fun toCharacterDTO(character: Character) : CharacterDTO {
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
}