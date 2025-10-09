package com.game.dungeonborn.controllers.dungeon

import com.game.dungeonborn.constant.Route
import com.game.dungeonborn.dto.dungeon.DungeonDTO
import com.game.dungeonborn.dto.dungeon.DungeonSlimDTO
import com.game.dungeonborn.dto.dungeon.RunDungeonDTO
import com.game.dungeonborn.dto.dungeon.RunDungeonResponseDTO
import com.game.dungeonborn.service.dungeon.DungeonService
import jakarta.validation.constraints.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Route.API_DUNGEON_ROUTE)
class DungeonController(
    private val dungeonService: DungeonService,
) {

    @GetMapping(Route.API_GET_ROUTE)
    fun getDungeon(
        @PathVariable @NotNull id: Long,
    ): ResponseEntity<DungeonDTO> {
        val dungeon = dungeonService.getDungeon(id);

        return ResponseEntity.ok(dungeon);
    }

    @GetMapping(Route.API_GET_ALL_ROUTE)
    fun getAllDungeons(): ResponseEntity<List<DungeonSlimDTO>> {
        val dungeons = dungeonService.getAllDungeons();

        return ResponseEntity.ok(dungeons);
    }

    @PostMapping(Route.API_RUN_DUNGEON_ROUTE)
    fun startDungeon(
        @RequestBody @Validated runDungeonDTO: RunDungeonDTO
    ): ResponseEntity<RunDungeonResponseDTO> {
        val startDungeonResult = dungeonService.runDungeon(runDungeonDTO);

        return ResponseEntity.ok(startDungeonResult);
    }
}