package com.game.dungeonborn.controllers.admin

import com.game.dungeonborn.constant.Route
import com.game.dungeonborn.dto.admin.AdminBootstrapDTO
import com.game.dungeonborn.dto.admin.AdminLoginDTO
import com.game.dungeonborn.dto.character.levels.CharacterLevelPointsAddDTO
import com.game.dungeonborn.dto.character.levels.CharacterLevelPointsAddResponseDTO
import com.game.dungeonborn.dto.user.UserRegistrationDTO
import com.game.dungeonborn.dto.user.UserRegistrationResponseDTO
import com.game.dungeonborn.service.user.UserService
import com.game.dungeonborn.service.utils.level.CharacterLevelService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping(Route.API_ADMIN_ROUTE)
class AdminController(
    private val userService: UserService,
    private val levelService: CharacterLevelService
) {

    @PostMapping(Route.API_LOGIN_ROUTE)
    fun login(@RequestBody @Validated admin: AdminLoginDTO): ResponseEntity<AdminBootstrapDTO> {


        return ResponseEntity.ok(AdminBootstrapDTO(""))
    }

    @PostMapping(Route.API_ADMIN_USER_CREATE_ROUTE)
    @PreAuthorize("hasAuthority('CREATE_USER')")
    fun createUser(@RequestBody @Validated user: UserRegistrationDTO): ResponseEntity<String> {

        val location: URI = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").build().toUri();

        return ResponseEntity.created(location).body("");
    }

    @PostMapping(Route.API_CHARACTER_LEVEL_ADD_POINTS)
    @PreAuthorize("hasAuthority('READ_CHAR')") //ADD_CHARACTER_LEVEL_POINTS
    fun addCharacterLevelPoints(
        @RequestBody @Validated addCharacterLevelPoints: CharacterLevelPointsAddDTO
    ): ResponseEntity<CharacterLevelPointsAddResponseDTO> {
        val addCharacterLevelPointsResponse = levelService.addCharacterLevelPoints(addCharacterLevelPoints);

        return ResponseEntity.ok(addCharacterLevelPointsResponse);
    }
}