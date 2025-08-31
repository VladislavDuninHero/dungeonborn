package com.game.dungeonborn.controllers.user

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.constant.Route
import com.game.dungeonborn.dto.bootstrap.BootstrapDTO
import com.game.dungeonborn.dto.user.RefreshTokenResponseDTO
import com.game.dungeonborn.dto.user.UserLoginDTO
import com.game.dungeonborn.dto.user.UserRegistrationDTO
import com.game.dungeonborn.dto.user.UserRegistrationResponseDTO
import com.game.dungeonborn.exception.user.RefreshTokenIsInvalidException
import com.game.dungeonborn.service.security.jwt.JwtService
import com.game.dungeonborn.service.user.UserService
import jakarta.servlet.http.HttpServletResponse
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
    ): ResponseEntity<BootstrapDTO> {
        val userLoginData = userService.login(user);

        val cookie = ResponseCookie.from("refreshToken", userLoginData.authorization.refreshToken)
            .httpOnly(true)
            .secure(true)
            .path(Route.API_USER_ROUTE)
            .maxAge(Duration.ofDays(14))
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(BootstrapDTO(userLoginData.authorization.accessToken));
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

    @PostMapping(Route.API_GET_ROUTE)
    @PreAuthorize("hasAuthority('READ_USER')")
    fun getUser(
        @PathVariable("id") id: String,
    ): ResponseEntity<String> {

        return ResponseEntity.ok("");
    }
}