package com.game.dungeonborn.service.dungeon

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.dto.battle.AttackDTO
import com.game.dungeonborn.dto.battle.BattleStepDTO
import com.game.dungeonborn.dto.character.CharacterBattleDTO
import com.game.dungeonborn.dto.dungeon.*
import com.game.dungeonborn.dto.npc.NpcDTO
import com.game.dungeonborn.entity.character.Character
import com.game.dungeonborn.entity.dungeon.Dungeon
import com.game.dungeonborn.entity.dungeon.DungeonLevel
import com.game.dungeonborn.exception.character.CharacterNotFoundException
import com.game.dungeonborn.exception.dungeon.DungeonNotFoundException
import com.game.dungeonborn.extensions.NpcMapper
import com.game.dungeonborn.extensions.character.CharacterMapper
import com.game.dungeonborn.repositories.DungeonLevelRepository
import com.game.dungeonborn.repositories.DungeonProgressRepository
import com.game.dungeonborn.repositories.DungeonRepository
import com.game.dungeonborn.service.utils.battle.CharacterBattleService
import com.game.dungeonborn.service.utils.battle.NpcBattleService
import com.game.dungeonborn.service.utils.character.CharacterUtils
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class DungeonUtils(
    private val characterUtils: CharacterUtils,
    private val dungeonLevelRepository: DungeonLevelRepository,
    private val dungeonProgressRepository: DungeonProgressRepository,
    private val dungeonRepository: DungeonRepository,
    private val dungeonLevelUtils: DungeonLevelUtils,
    private val npcMapper: NpcMapper,
    private val characterBattleService: CharacterBattleService,
    private val npcBattleService: NpcBattleService,
    private val characterMapper: CharacterMapper
) {
    fun getAvailableDungeonsForCharacter(characterId: Long): List<DungeonSlimDTO> {
        val foundCharacter = characterUtils.findCharacterById(characterId);
        val dungeons = dungeonRepository.findAll();

        return dungeons.map { dungeon ->
            DungeonSlimDTO(
                id = dungeon.id ?: 0,
                name = dungeon.name,
                dungeonLevels = dungeon.dungeonLevels
                    .map {
                        DungeonLevelSlimDTO(
                            id = it.id,
                            lvl = it.lvl,
                            recommendedPower = it.recommendedPower
                        )
                    }
            )
        }
    }

    fun findDungeonByIdAndGet(id: Long): Dungeon {
        return dungeonRepository.findById(id)
            .orElseThrow { throw DungeonNotFoundException(ExceptionMessage.DUNGEON_NOT_FOUND) };
    }

    fun calculateDungeonRunResult(
        character: Character,
        dungeon: Dungeon,
        dungeonLevel: DungeonLevel,
    ): BattleResultDTO {
        val rounds = generateDungeonRounds(dungeonLevel);
        val battleResult = calculateBattleResult(character, rounds);

        return battleResult;
    }

    private fun generateDungeonRounds(dungeonLevel: DungeonLevel): MutableMap<Int, MutableList<NpcDTO>> {
        val dungeonLevelNpcs = dungeonLevel.npcs;
        val rounds: MutableMap<Int, MutableList<NpcDTO>> = mutableMapOf();

        val boss = dungeonLevelNpcs.find { it.isBoss == true };
        val defaultNpcs = dungeonLevelNpcs.filter { it.isBoss == false };
        val roundNpcStatistic: MutableMap<Long, Int> = mutableMapOf();

        defaultNpcs.forEach { roundNpcStatistic[it.id ?: 0] = 0 };

        for (i in 0 until dungeonLevel.dungeonRounds) {
            if (boss != null && i == dungeonLevel.dungeonRounds) {
                rounds[i] = mutableListOf(npcMapper.toDTO(boss))

                break;
            }

            rounds[i] = mutableListOf();

            for (k in 0 until dungeonLevel.totalEnemiesNpc) {
                val currentNpc = defaultNpcs.random();
                rounds[i]?.add(npcMapper.toDTO(currentNpc));

                roundNpcStatistic[currentNpc.id ?: 0] = roundNpcStatistic.getOrDefault(currentNpc.id, 0) + 1;
            }
        }

        for (npcStatistic in roundNpcStatistic.entries) {
            if (npcStatistic.value == 0) {
                val npc = defaultNpcs.find { it.id == npcStatistic.key } ?: continue;

                rounds[Random.nextInt(dungeonLevel.dungeonRounds - 1)]
                    ?.add(npcMapper.toDTO(npc))
            }
        }

        return rounds;
    }

    private fun calculateBattleResult(
        character: Character,
        rounds: MutableMap<Int, MutableList<NpcDTO>>
    ): BattleResultDTO {
        val characterDTO = characterMapper.toBattleCharacterDTO(character);
        var roundCounter = 0;
        var characterIsAlive = true;
        val roundResults = mutableMapOf<Int, MutableList<BattleStepDTO>>()

        for (round in rounds) {
            val npcInRound = round.value;
            val battleSteps: MutableList<BattleStepDTO> = mutableListOf();
            var npcCount = npcInRound.size;
            var stepCounter = 0;

            while (npcCount > 0 && characterIsAlive) {
                stepCounter++;

                val randomNpc = npcInRound.random();

                val characterAttack = characterBattleService.attack(character);
                val npcAttack = npcBattleService.attack(randomNpc);

                armorAffect(characterAttack, npcAttack, characterDTO, randomNpc);

                randomNpc.maxHp -= characterAttack.totalDamage;
                characterDTO.maxHp -= randomNpc.maxDamage;

                if (randomNpc.maxHp <= 0) {
                    npcCount--;
                }

                if (characterDTO.maxHp <= 0) {
                    characterIsAlive = false;
                }

                battleSteps.add(
                    BattleStepDTO(
                        stepNumber = stepCounter,
                        characterAttack = characterAttack,
                        npcAttack = npcAttack,
                        characterHp = characterDTO.maxHp,
                        npcHp = randomNpc.maxHp,
                    )
                )
            }

            roundResults[roundCounter] = battleSteps;

            roundCounter++;
        }

        return BattleResultDTO(
            characterIsAlive,
            roundResults
        );
    }

    private fun armorAffect(
        characterAttack: AttackDTO,
        npcAttack: AttackDTO,
        character: CharacterBattleDTO,
        npc: NpcDTO
    ) {
        characterAttack.totalDamage -= npc.armor;
        npcAttack.totalDamage -= character.armor;
    }
}