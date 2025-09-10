package com.game.dungeonborn.controllers.user

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.constant.Message
import com.game.dungeonborn.constant.Route
import com.game.dungeonborn.dto.bootstrap.BootstrapDTO
import com.game.dungeonborn.dto.character.CharacterDTO
import com.game.dungeonborn.dto.official.SuccessMessageDTO
import com.game.dungeonborn.dto.user.*
import com.game.dungeonborn.exception.user.RefreshTokenIsInvalidException
import com.game.dungeonborn.service.bootstrap.BootstrapService
import com.game.dungeonborn.service.character.CharacterService
import com.game.dungeonborn.service.security.jwt.JwtService
import com.game.dungeonborn.service.user.UserService
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.time.Duration

@RestController
@RequestMapping(Route.API_USER_ROUTE)
class UserController(
    private val userService: UserService,
    private val jwtService: JwtService,
    private val characterService: CharacterService,
    private val bootstrapService: BootstrapService,
) {

    @PostMapping(Route.API_USER_REGISTRATION_ROUTE)
    fun registration(@RequestBody @Validated user: UserRegistrationDTO): ResponseEntity<UserRegistrationResponseDTO> {
        val createdUser = userService.registration(user);

        val location: URI = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").build().toUri();

        return ResponseEntity.created(location).body(createdUser);
    }

    @PostMapping(Route.API_LOGIN_ROUTE)
    fun login(
        @RequestBody
        @Validated user: UserLoginDTO,
        response: HttpServletResponse
    ): ResponseEntity<UserLoginResponseSlimDTO> {
        val userLoginData = userService.login(user);

        val cookie = ResponseCookie.from("refreshToken", userLoginData.authorization.refreshToken)
            .httpOnly(true)
            .secure(true)
            .path(Route.API_USER_ROUTE)
            .maxAge(Duration.ofDays(14))
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(UserLoginResponseSlimDTO(
            userLoginData.login,
            userLoginData.authorization.accessToken
        ));
    }

    @GetMapping(Route.API_BOOTSTRAP_ROUTE)
    @PreAuthorize("hasAuthority('READ_USER')")
    fun bootstrap(
        @PathVariable @NotNull login: String,
    ): ResponseEntity<BootstrapDTO> {
        val bootstrap = bootstrapService.getBootstrap(login);

        return ResponseEntity.ok(bootstrap);
    }

    @PostMapping(Route.API_USER_TOKEN_REFRESH_ROUTE)
    fun refreshToken(
        @CookieValue("refreshToken") refreshToken: String?
    ): ResponseEntity<RefreshTokenResponseDTO> {
        if (refreshToken == null) {
            throw RefreshTokenIsInvalidException(ExceptionMessage.REFRESH_TOKEN_IS_INVALID);
        }

        val user = jwtService.validateRefreshToken(refreshToken);

        val newAccessToken = jwtService.generateAccessToken(user.login);

        return ResponseEntity.ok(RefreshTokenResponseDTO(newAccessToken));
    }

    @GetMapping(Route.API_GET_ROUTE)
    @PreAuthorize("hasAuthority('READ_USER')")
    fun getUser(
        @PathVariable("id") id: Long,
    ): ResponseEntity<UserDTO> {
        val foundedUser = userService.getUserById(id);

        return ResponseEntity.ok(foundedUser);
    }

    @PutMapping(Route.API_UPDATE_ROUTE)
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    fun updateUser(
        @PathVariable("id") id: Long,
        @RequestBody @Validated user: UpdateUserDTO,
    ): ResponseEntity<UpdateUserResponseDTO> {
        val updatedUser = userService.updateUser(id, user);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping(Route.API_DELETE_ROUTE)
    @PreAuthorize("hasAuthority('DELETE_USER')")
    fun deleteUser(
        @PathVariable("id") id: Long,
    ): ResponseEntity<SuccessMessageDTO> {
        userService.disableUser(id);

        return ResponseEntity.ok(SuccessMessageDTO(Message.DEFAULT_SUCCESS_MESSAGE));
    }

    @PostMapping(Route.API_RECOVERY_ROUTE)
    @PreAuthorize("hasAuthority('DELETE_USER')")
    fun recoveryUser(
        @PathVariable("id") id: Long,
    ): ResponseEntity<SuccessMessageDTO> {
        userService.recoveryUser(id);

        return ResponseEntity.ok(SuccessMessageDTO(Message.DEFAULT_SUCCESS_MESSAGE));
    }

    @GetMapping(Route.API_GET_ALL_CHARACTERS_ROUTE)
    @PreAuthorize("hasAuthority('READ_CHAR')")
    fun getAllCharactersByUserId(
        @PathVariable id: Long
    ): ResponseEntity<List<CharacterDTO>> {
        val foundedCharacter = characterService.getAllCharactersForUserId(id);

        return ResponseEntity.ok(foundedCharacter);
    }
}