package com.game.dungeonborn.service.utils.level

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.dto.character.levels.CharacterLevelPointsAddDTO
import com.game.dungeonborn.dto.character.levels.CharacterLevelPointsAddResponseDTO
import com.game.dungeonborn.dto.character.levels.CharacterLevelUpDTO
import com.game.dungeonborn.dto.character.levels.CharacterLevelUpResponseDTO
import com.game.dungeonborn.exception.NotImplementedException
import com.game.dungeonborn.repositories.CharacterLevelBonusRepository
import com.game.dungeonborn.repositories.CharacterRepository
import com.game.dungeonborn.service.stat.CharacterStatsUtils
import com.game.dungeonborn.service.utils.character.CharacterUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class CharacterLevelService(
    private val characterUtils: CharacterUtils,
    private val characterLevelBonusRepository: CharacterLevelBonusRepository,
    private val characterRepository: CharacterRepository,
    private val statsUtils: CharacterStatsUtils
) {

    fun levelUp(levelUpDTO: CharacterLevelUpDTO): CharacterLevelUpResponseDTO {
        return CharacterLevelUpResponseDTO(1);
    }

    @Transactional
    fun addCharacterLevelPoints(characterLevelPointsAddDTO: CharacterLevelPointsAddDTO): CharacterLevelPointsAddResponseDTO {
        val foundCharacter = characterUtils.findCharacterById(characterLevelPointsAddDTO.characterId);
        val characterLevel = foundCharacter.characterLevel;
        val characterExperience = foundCharacter.totalExperience;
        var isLevelUp = false;

        val calculatedExperience = characterExperience + characterLevelPointsAddDTO.points;

        val levels = characterLevelBonusRepository.findAll();
        val lastLevel = levels.last();
        var nextLevelPoints = levels.find { it.levelNumber == characterLevel }?.totalPoints
            ?: lastLevel.totalPoints ?: 0.0;

        var newLevelNumber = characterLevel;

        if (calculatedExperience >= nextLevelPoints) {

            for (level in levels) {
                if (calculatedExperience >= (lastLevel.totalPoints ?: 0.0)) {
                    newLevelNumber = lastLevel.levelNumber ?: characterLevel;
                    break;
                }

                val totalPoints = level.totalPoints ?: 0.0;
                val levelNumber = level.levelNumber ?: 0;

                if (calculatedExperience > totalPoints) {
                    continue;
                }

                if (calculatedExperience <= totalPoints) {
                    newLevelNumber = levelNumber + 1;
                    break;
                }
            }

            foundCharacter.characterLevel = newLevelNumber;
            isLevelUp = if (newLevelNumber == (lastLevel.levelNumber ?: 0)) false else true;
            nextLevelPoints = levels.find { it.levelNumber == newLevelNumber }?.totalPoints
                ?: lastLevel.totalPoints ?: 0.0;
            statsUtils.recalculateCharacterStats(foundCharacter);
        }

        foundCharacter.totalExperience = calculatedExperience;

        characterRepository.save(foundCharacter);

        return CharacterLevelPointsAddResponseDTO(
            newLevelNumber,
            calculatedExperience,
            isLevelUp,
            nextLevelPoints
        );
    }
}