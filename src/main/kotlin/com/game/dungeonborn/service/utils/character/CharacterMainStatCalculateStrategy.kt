package com.game.dungeonborn.service.utils.character

import com.game.dungeonborn.enums.stat.MainStat
import org.springframework.stereotype.Component

@Component
class CharacterMainStatCalculateStrategy(
    private val mainStatCalculateStrategyMap: Map<MainStat, String> = mapOf(

    )
) {
}