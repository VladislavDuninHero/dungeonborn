package com.game.dungeonborn.service.stat

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.enums.stat.MainStat
import com.game.dungeonborn.enums.stat.SecondaryStat
import com.game.dungeonborn.exception.NotImplementedException
import org.springframework.stereotype.Service

@Service
class MainStatCoefficientFactory(
    private val mainStatsCoefficients: Map<MainStat, Map<SecondaryStat, Double>> = mapOf(
        MainStat.STRENGTH to mapOf(
            SecondaryStat.DAMAGE to 2.0
        ),
        MainStat.INTELLECT to mapOf(
            SecondaryStat.MANA to 10.0,
            SecondaryStat.DAMAGE to 2.0
        ),
        MainStat.AGILITY to mapOf(
            SecondaryStat.DAMAGE to 2.0,
            SecondaryStat.CRITICAL_POWER to 0.5
        ),
        MainStat.STAMINA to mapOf(
            SecondaryStat.HP to 2.0
        )
    )
) {
    fun getSecondaryStatCoefficient(mainStat: MainStat, secondaryStat: SecondaryStat): Double {
        if (mainStatsCoefficients[mainStat] == null) {
            throw NotImplementedException(String.format(ExceptionMessage.NOT_IMPLEMENTED, mainStat));
        }
        if (mainStatsCoefficients[mainStat]?.get(secondaryStat) == null) {
            throw NotImplementedException(String.format(ExceptionMessage.NOT_IMPLEMENTED, secondaryStat));
        }

        return mainStatsCoefficients[mainStat]?.get(secondaryStat) ?: 0.0
    }
}