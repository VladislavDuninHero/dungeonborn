package com.game.dungeonborn.service.dungeon

import com.game.dungeonborn.dto.character.levels.CharacterLevelPointsAddDTO
import com.game.dungeonborn.dto.dungeon.*
import com.game.dungeonborn.entity.character.CharacterInventoryItem
import com.game.dungeonborn.entity.dungeon.DungeonProgress
import com.game.dungeonborn.repositories.CharacterRepository
import com.game.dungeonborn.repositories.DungeonProgressRepository
import com.game.dungeonborn.repositories.DungeonRepository
import com.game.dungeonborn.service.loot.LootService
import com.game.dungeonborn.service.utils.character.CharacterUtils
import com.game.dungeonborn.service.utils.level.CharacterLevelService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DungeonService(
    private val dungeonRepository: DungeonRepository,
    private val dungeonUtils: DungeonUtils,
    private val characterUtils: CharacterUtils,
    private val dungeonProgressRepository: DungeonProgressRepository,
    private val dungeonLevelUtils: DungeonLevelUtils,
    private val characterLevelService: CharacterLevelService,
    private val lootService: LootService,
    private val characterRepository: CharacterRepository,
) {

    fun getDungeon(id: Long): DungeonDTO {
        val foundDungeon = dungeonUtils.findDungeonByIdAndGet(id);

        val dungeon = DungeonDTO(
            foundDungeon.id ?: 0,
            foundDungeon.name,
            foundDungeon.dungeonLevels.map {
                DungeonLevelDTO(
                    it.id,
                    it.lvl,
                    it.recommendedPower,
                    it.totalEnemiesNpc,
                    it.ratingPoints,
                    it.dungeonRounds,
                    it.dungeon?.id,
                    it.dungeonLoot?.id
                )
            }
        );

        return dungeon;
    }

    fun getAllDungeons(): List<DungeonSlimDTO> {
        val dungeons = dungeonRepository.findAll();

        return dungeons.map { dungeon ->
            DungeonSlimDTO(
                dungeon.id ?: 0,
                dungeon.name,
                dungeon.dungeonLevels.map {
                    DungeonLevelSlimDTO(
                        it.id,
                        it.lvl,
                        it.recommendedPower,
                    )
                }
            )
        };
    }

    @Transactional
    fun runDungeon(runDungeonDTO: RunDungeonDTO): RunDungeonResponseDTO {
        val foundCharacter = characterUtils.findCharacterById(runDungeonDTO.characterId);
        val foundDungeon = dungeonUtils.findDungeonByIdAndGet(runDungeonDTO.dungeonId);
        val foundDungeonLevel = dungeonLevelUtils.findDungeonLevelById(runDungeonDTO.dungeonLevelId);

        val calculatedDungeonResult = dungeonUtils.calculateDungeonRunResult(
            foundCharacter,
            foundDungeon,
            foundDungeonLevel
        )

        val characterInventoryLoot: MutableList<CharacterInventoryItem> = mutableListOf();
        val exp = foundDungeonLevel.dungeonLoot?.expReward ?: 0.0;

        if (calculatedDungeonResult.characterIsAlive) {
            val dungeonProgress = DungeonProgress();
            dungeonProgress.dungeon = foundDungeon;
            dungeonProgress.dungeonLevel = foundDungeonLevel;
            dungeonProgress.character = foundCharacter;

            characterLevelService.addCharacterLevelPoints(
                CharacterLevelPointsAddDTO(
                foundCharacter.id ?: 0,
                    exp
                )
            );

            val loot = foundDungeonLevel.dungeonLoot?.lootTable?.lootTableItems ?: mutableListOf();
            val calculatedLoot = lootService.calculateDrop(loot);

            if ((foundCharacter.characterInventory?.items?.size ?: 0) < 25) {
                calculatedLoot.forEach {
                    val inventoryItem = CharacterInventoryItem();

                    inventoryItem.inventory = foundCharacter.characterInventory;
                    inventoryItem.item = it.item;

                    characterInventoryLoot.add(inventoryItem);
                }

                foundCharacter.characterInventory?.items?.addAll(characterInventoryLoot);

                dungeonProgressRepository.save(dungeonProgress);
                characterRepository.save(foundCharacter);
            }
        }

        return RunDungeonResponseDTO(
            foundCharacter.id ?: 0,
            runDungeonDTO.dungeonId,
            runDungeonDTO.dungeonLevelId,
            calculatedDungeonResult,
            DungeonLootDTO(
                exp,
                characterInventoryLoot.map { it.item?.id ?: 0 }.toList()
            )
        );
    }

    fun getCharacterAvailableDungeonsByCharacterId(userId: Long): List<DungeonSlimDTO> {
        val foundCharacter = characterUtils.findCharacterById(userId);

        val availableDungeons = dungeonUtils.getAvailableDungeonsForCharacter(userId);

        return availableDungeons;
    }
}