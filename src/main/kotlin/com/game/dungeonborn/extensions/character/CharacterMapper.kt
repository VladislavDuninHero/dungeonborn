package com.game.dungeonborn.extensions.character

import com.game.dungeonborn.dto.character.CharacterBattleDTO
import com.game.dungeonborn.dto.character.CharacterDTO
import com.game.dungeonborn.dto.character.CharacterSlimDTO
import com.game.dungeonborn.dto.character.inventory.InventoryItemDTO
import com.game.dungeonborn.dto.dungeon.BattleResultDTO
import com.game.dungeonborn.dto.item.ItemDTO
import com.game.dungeonborn.entity.character.Character
import com.game.dungeonborn.enums.item.ItemQuality
import com.game.dungeonborn.enums.item.ItemType
import com.game.dungeonborn.enums.item.SlotType
import com.game.dungeonborn.exception.RequiredFieldException
import com.game.dungeonborn.extensions.stats.CharacterStatsMapper
import com.game.dungeonborn.service.stat.CharacterStatsUtils
import com.game.dungeonborn.service.utils.character.CharacterUtils
import com.game.dungeonborn.service.utils.level.CharacterLevelService
import org.springframework.stereotype.Component

@Component
class CharacterMapper(
    private val characterUtils: CharacterUtils,
    private val characterStatsUtils: CharacterStatsUtils,
    private val characterStatsMapper: CharacterStatsMapper,
    private val characterLevelService: CharacterLevelService,
) {
    fun toCharacterDTO(character: Character) : CharacterDTO {
        val characterId = character.id ?: throw RequiredFieldException("Character id is required");

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
        val characterStats = characterStatsUtils.findCharacterStatsByCharacterIdAndGet(characterId);
        val mappedInventory = character.characterInventory?.items?.map {
            InventoryItemDTO(
                it.id ?: 0,
                ItemDTO(
                    it.item?.id ?: 0,
                    it.item?.name ?: "Unknown",
                    it.item?.type ?: ItemType.UNKNOWN,
                    it.item?.slotType ?: SlotType.UNKNOWN,
                    it.item?.itemLevel ?: 0.0,
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

        val nextLevelPoints = characterLevelService.getCharacterLevelByLevelNumber(character.characterLevel)
            .totalPoints ?: 0.0;

        return CharacterDTO(
            character.id ?: 0,
            character.name,
            character.characterLevel,
            character.totalExperience,
            nextLevelPoints,
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

    fun toBattleCharacterDTO(character: Character) : CharacterBattleDTO {
        return CharacterBattleDTO(
            character.characterStat?.totalHp ?: 0.0,
            character.characterStat?.totalArmor ?: 0.0,
        )
    }
}