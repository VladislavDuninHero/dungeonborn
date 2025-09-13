package com.game.dungeonborn.service.stat

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.entity.character.CharacterStats
import com.game.dungeonborn.exception.character.CharacterStatsNotFound
import com.game.dungeonborn.service.utils.character.CharacterUtils
import org.springframework.stereotype.Service

@Service
class CharacterStatsUtils(
    private val characterUtils: CharacterUtils
) {
    fun findCharacterStatsByCharacterIdAndGet(characterId: Long): CharacterStats {
        val character = characterUtils.findCharacterById(characterId);

        return character.characterStat ?: throw CharacterStatsNotFound(ExceptionMessage.CHARACTER_STATS_NOT_FOUND);
    }
}