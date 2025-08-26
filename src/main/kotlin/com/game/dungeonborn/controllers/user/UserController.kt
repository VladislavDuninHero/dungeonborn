package com.game.dungeonborn.controllers.user

import com.game.dungeonborn.constant.Route
import com.game.dungeonborn.dto.bootstrap.BootstrapDTO
import com.game.dungeonborn.dto.user.UserLoginDTO
import com.game.dungeonborn.dto.user.UserRegistrationDTO
import com.game.dungeonborn.dto.user.UserRegistrationResponseDTO
import com.game.dungeonborn.service.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping(Route.API_USER_ROUTE)
class UserController(
    private val userService: UserService
) {

    @PostMapping(Route.API_USER_REGISTRATION_ROUTE)
    fun registration(@RequestBody @Validated user: UserRegistrationDTO): ResponseEntity<UserRegistrationResponseDTO> {
        val createdUser = userService.registration(user);

        val location: URI = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").build().toUri();

        return ResponseEntity.created(location).body(createdUser);
    }

    @PostMapping(Route.API_USER_REGISTRATION_ROUTE)
    fun login(@RequestBody @Validated user: UserLoginDTO): ResponseEntity<BootstrapDTO> {
        val bootstrap = userService.login(user);

        return ResponseEntity.ok(bootstrap);
    }
}