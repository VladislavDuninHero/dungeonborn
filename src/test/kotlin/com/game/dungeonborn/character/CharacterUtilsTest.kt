package com.game.dungeonborn.character

import com.game.dungeonborn.entity.character.CharacterLevelBonus
import com.game.dungeonborn.enums.stat.MainStat
import com.game.dungeonborn.enums.stat.SecondaryStat
import com.game.dungeonborn.repositories.CharacterLevelBonusRepository
import com.game.dungeonborn.repositories.CharacterRepository
import com.game.dungeonborn.service.stat.MainStatCoefficientFactory
import com.game.dungeonborn.service.utils.character.CharacterUtils
import com.game.dungeonborn.service.utils.level.CharacterLevelUtils
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CharacterUtilsTest {

    private val characterRepository = mockk<CharacterRepository>()
    private val mainStatCoefficientFactory = mockk<MainStatCoefficientFactory>()
    private val levelUtils = mockk<CharacterLevelUtils>();
    private val characterUtils = CharacterUtils(
        characterRepository,
        mainStatCoefficientFactory,
        levelUtils
    )

    @Test
    fun `calculateCharacterMana should be return correct mana for given intellect`() {
        //Arrange
        val intellect = 10.0;
        val level = 1;
        val expectedMana = 10.0;

        every { levelUtils.getLevelBonusByLevelNumber(level) } returns CharacterLevelBonus(
            bonusIntellect = 1.0
        )
        every {
            mainStatCoefficientFactory.getSecondaryStatCoefficient(MainStat.INTELLECT, SecondaryStat.MANA)
        } returns 10.0

        //Act
        val result = characterUtils.calculateCharacterMana(intellect, level);

        //Assert
        assertEquals(expectedMana, result);
    }
}