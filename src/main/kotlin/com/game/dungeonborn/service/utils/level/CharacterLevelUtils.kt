package com.game.dungeonborn.service.utils.level

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.entity.character.CharacterLevelBonus
import com.game.dungeonborn.exception.level.CharacterLevelNotFoundException
import com.game.dungeonborn.repositories.CharacterLevelBonusRepository
import org.springframework.stereotype.Service

@Service
class CharacterLevelUtils(
    private val characterLevelBonusRepository: CharacterLevelBonusRepository
) {
    fun getLevelBonusByLevelNumber(level: Int): CharacterLevelBonus {
        return characterLevelBonusRepository.findLevelByLevelNumber(level)
            .orElseThrow {
                throw CharacterLevelNotFoundException(
                    String.format(ExceptionMessage.CHARACTER_LEVEL_NOT_FOUND, level)
                )
            }
    }
}