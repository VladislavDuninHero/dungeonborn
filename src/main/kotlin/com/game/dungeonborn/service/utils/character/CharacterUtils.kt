package com.game.dungeonborn.service.utils.character

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.entity.character.Character
import com.game.dungeonborn.entity.character.CharacterEquipment
import com.game.dungeonborn.entity.item.Item
import com.game.dungeonborn.entity.character.CharacterClass
import com.game.dungeonborn.enums.stat.MainStat
import com.game.dungeonborn.enums.stat.SecondaryStat
import com.game.dungeonborn.exception.character.CharacterNameIsTakenException
import com.game.dungeonborn.exception.character.CharacterNotFoundException
import com.game.dungeonborn.repositories.CharacterRepository
import com.game.dungeonborn.service.stat.MainStatCoefficientFactory
import com.game.dungeonborn.service.utils.level.CharacterLevelUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*

@Service
class CharacterUtils(
    private val characterRepository: CharacterRepository,
    private val mainStatsCoefficientsFactory: MainStatCoefficientFactory,
    private val levelUtils: CharacterLevelUtils,
) {

    fun calculateCharacterCriticalPower(): Double {
        return 0.0;
    }

    fun calculateCharacterMana(intellect: Double, level: Int, characterId: Long? = null): Double {
        if (characterId != null) {
            val character = findCharacterById(characterId);
            val totalIntellect = character.characterStat?.totalIntellect ?: 0.0;
            val currentTotalMana = character.characterStat?.totalMana ?: 0.0;

            return currentTotalMana + (totalIntellect * mainStatsCoefficientsFactory
                .getSecondaryStatCoefficient(MainStat.INTELLECT, SecondaryStat.MANA));
        }

        val levelBonus = levelUtils.getLevelBonusByLevelNumber(level).bonusIntellect ?: 0.0;
        val coefficient = mainStatsCoefficientsFactory
            .getSecondaryStatCoefficient(MainStat.INTELLECT, SecondaryStat.MANA)
        val totalMana = levelBonus * coefficient;

        return totalMana;
    }

    fun calculateCharacterGearScore(): Double {
        return 0.0;
    }

    fun calculateCharacterDamage(mainStat: MainStat, characterId: Long? = null): Double {
        val coefficient = mainStatsCoefficientsFactory.getSecondaryStatCoefficient(mainStat, SecondaryStat.DAMAGE);

        if (characterId != null) {
            val character = findCharacterById(characterId);
            val currentTotalDamage = character.characterStat?.totalDamage ?: 0.0;


        }

        return 0.0;
    }

    fun calculateCharacterArmor(): Double {
        return 0.0;
    }

    fun findCharacterById(id: Long): Character {
        return characterRepository.findCharacterById(id)
            .orElseThrow { throw CharacterNotFoundException(ExceptionMessage.CHARACTER_NOT_FOUND) };
    }

    fun findCharacterByName(name: String): Character {
        return characterRepository.findCharacterByName(name)
            .orElseThrow { throw CharacterNotFoundException(ExceptionMessage.CHARACTER_NOT_FOUND) };
    }

    fun validateCharacterName(name: String) {
        characterRepository.findCharacterByName(name).ifPresent {
            throw CharacterNameIsTakenException(ExceptionMessage.CHARACTER_NAME_IS_TAKEN);
        }
    }

    fun findAllCharactersByUserId(id: Long): List<Character> {
        return characterRepository.findAllCharactersByUserId(id);
    }

    fun getSecondStatCoefficient(mainStat: MainStat): Double {
        return mainStatsCoefficientsFactory.getSecondaryStatCoefficient(mainStat, SecondaryStat.DAMAGE)
    }

    fun getBaseDamageForCharacterClassMainStat(characterClass: CharacterClass): Double {
        val baseStatsValueMap: Map<MainStat, Double> = mapOf(
            MainStat.INTELLECT to characterClass.baseIntellect,
            MainStat.AGILITY to characterClass.baseAgility,
            MainStat.STRENGTH to characterClass.baseStrength,
            MainStat.STAMINA to characterClass.baseHp
        );

        val baseMainStatValue = baseStatsValueMap[characterClass.mainStat];

        return baseMainStatValue ?: 0.0;
    }

    fun convertCharacterEquipmentToList(characterEquipment: CharacterEquipment?): List<Item?> {
        return mutableListOf(
            characterEquipment?.headItem,
            characterEquipment?.shouldersItem,
            characterEquipment?.chestItem,
            characterEquipment?.handsItem,
            characterEquipment?.legsItem,
            characterEquipment?.feetItem,
            characterEquipment?.weaponItem
        );
    }

    fun extractStrengthFromEquipment(character: Character): Double {
        val equipmentList = convertCharacterEquipmentToList(character.characterEquipment);

        return equipmentList.sumOf { it?.strength ?: 0.0 }
    }

    fun extractIntellectFromEquipment(character: Character): Double {
        val equipmentList = convertCharacterEquipmentToList(character.characterEquipment);

        return equipmentList.sumOf { it?.intellect ?: 0.0 }
    }

    fun extractStaminaFromEquipment(character: Character): Double {
        val equipmentList = convertCharacterEquipmentToList(character.characterEquipment);

        return equipmentList.sumOf { it?.stamina ?: 0.0 }
    }

    fun extractAgilityFromEquipment(character: Character): Double {
        val equipmentList = convertCharacterEquipmentToList(character.characterEquipment);

        return equipmentList.sumOf { it?.agility ?: 0.0 }
    }

}