package com.game.dungeonborn.service.stat

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.entity.character.Character
import com.game.dungeonborn.entity.character.CharacterStats
import com.game.dungeonborn.enums.stat.MainStat
import com.game.dungeonborn.exception.RequiredFieldException
import com.game.dungeonborn.exception.character.CharacterStatsNotFound
import com.game.dungeonborn.repositories.CharacterRepository
import com.game.dungeonborn.service.utils.character.CharacterUtils
import com.game.dungeonborn.service.utils.level.CharacterLevelUtils
import org.springframework.stereotype.Service

@Service
class CharacterStatsUtils(
    private val characterUtils: CharacterUtils,
    private val levelUtils: CharacterLevelUtils,
    private val characterRepository: CharacterRepository
) {
    fun findCharacterStatsByCharacterIdAndGet(characterId: Long): CharacterStats {
        val character = characterUtils.findCharacterById(characterId);

        return character.characterStat ?: throw CharacterStatsNotFound(ExceptionMessage.CHARACTER_STATS_NOT_FOUND);
    }

    fun recalculateCharacterStats(character: Character) {
        val characterStats = findCharacterStatsByCharacterIdAndGet(character.id ?: 0);
        val mainStat = character.characterClass?.mainStat ?: throw RequiredFieldException("Character main stat is required");
        val level = levelUtils.getLevelBonusByLevelNumber(character.characterLevel);

        val currentCharacterTotalHp = characterStats.totalHp ?: 0.0;
        val currentCharacterTotalMana = characterStats.totalMana ?: 0.0;
        val currentCharacterStrength = characterStats.totalStrength ?: 0.0;
        val currentCharacterAgility = characterStats.totalAgility ?: 0.0;
        val currentCharacterIntellect = characterStats.totalIntellect ?: 0.0;
        val currentCharacterCriticalChance = characterStats.totalCriticalChance ?: 0.0;
        val currentCharacterCriticalPower = characterStats.totalCriticalPower ?: 0.0;
        val currentCharacterGearScore = characterStats.totalGearScore ?: 0.0;
        val currentCharacterTotalDamage = characterStats.totalDamage ?: 0.0;
        val currentCharacterTotalArmor = characterStats.totalArmor ?: 0.0;

        val levelBonusStamina = level.bonusHp ?: 0.0;
        val levelBonusStrength = level.bonusStrength ?: 0.0;
        val levelBonusIntellect = level.bonusIntellect ?: 0.0;
        val levelBonusAgility = level.bonusAgility ?: 0.0;

        val totalStamina = characterUtils.extractStaminaFromEquipment(character) + levelBonusStamina;
        val totalStrength = characterUtils.extractStrengthFromEquipment(character) + levelBonusStrength;
        val totalIntellect = characterUtils.extractIntellectFromEquipment(character) + levelBonusIntellect;
        val totalAgility = characterUtils.extractAgilityFromEquipment(character) + levelBonusAgility;
        val totalCriticalPower = characterUtils.extractCriticalPowerFromEquipment(character);
        val totalArmor = characterUtils.extractArmorFromEquipment(character);
        val totalCriticalChance = characterUtils.extractCriticalChanceFromEquipment(character);
        val totalGearScore = characterUtils.extractGearScoreFromEquipment(character);

        val totalMainStat = when(mainStat) {
            MainStat.STRENGTH -> totalStrength;
            MainStat.AGILITY -> totalAgility;
            MainStat.INTELLECT -> totalIntellect;
            else -> throw RequiredFieldException("Main stat is required");
        }

        val calculatedTotalHp = characterUtils.calculateEquipmentBonusCharacterHp(totalStamina);
        val calculatedTotalMana = characterUtils.calculateEquipmentBonusCharacterMana(totalIntellect)
        val calculatedTotalCriticalPower = characterUtils.calculateEquipmentCharacterCriticalPower(totalCriticalPower);
        val calculatedTotalDamage = characterUtils.calculateCharacterDamage(mainStat, totalMainStat);

        characterStats.totalHp = currentCharacterTotalHp + calculatedTotalHp;
        characterStats.totalMana = currentCharacterTotalMana + calculatedTotalMana;
        characterStats.totalStrength = currentCharacterStrength + totalStrength;
        characterStats.totalIntellect = currentCharacterIntellect + totalIntellect;
        characterStats.totalAgility = currentCharacterAgility + totalAgility;
        characterStats.totalCriticalChance = currentCharacterCriticalChance + totalCriticalChance;
        characterStats.totalCriticalPower = currentCharacterCriticalPower + calculatedTotalCriticalPower;
        characterStats.totalDamage = currentCharacterTotalDamage + calculatedTotalDamage;
        characterStats.totalArmor = currentCharacterTotalArmor + totalArmor;
        characterStats.totalGearScore = currentCharacterGearScore + totalGearScore;

        characterRepository.save(character);
    }
}