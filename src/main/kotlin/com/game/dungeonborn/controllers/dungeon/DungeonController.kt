package com.game.dungeonborn.controllers.dungeon

import com.game.dungeonborn.constant.Route
import com.game.dungeonborn.dto.dungeon.DungeonDTO
import com.game.dungeonborn.dto.dungeon.DungeonSlimDTO
import com.game.dungeonborn.service.dungeon.DungeonService
import jakarta.validation.constraints.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}