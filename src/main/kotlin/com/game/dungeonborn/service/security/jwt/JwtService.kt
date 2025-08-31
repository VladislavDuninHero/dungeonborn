package com.game.dungeonborn.service.security.jwt

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.entity.user.Permission
import com.game.dungeonborn.entity.user.Role
import com.game.dungeonborn.entity.user.User
import com.game.dungeonborn.exception.user.RefreshTokenIsInvalidException
import com.game.dungeonborn.exception.user.RefreshTokenNotFoundException
import com.game.dungeonborn.exception.user.UserNotFoundException
import com.game.dungeonborn.properties.JwtSecretProperties
import com.game.dungeonborn.repositories.RefreshTokenRepository
import com.game.dungeonborn.repositories.UserRepository
import com.game.dungeonborn.service.role.RoleService
import com.game.dungeonborn.service.utils.user.UserUtils
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService(
    private val jwtSecretProperties: JwtSecretProperties,
    private val roleService: RoleService,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userUtils: UserUtils
) {

    fun generateAccessToken(login: String): String {
        val claims: MutableMap<String, Any> = mutableMapOf();

        val permissions: MutableSet<Permission> = extractPermissions(roleService.getRoleForUser(login))
        val authorities = permissions.map {it.authority}.toMutableList();

        claims["authorities"] = authorities;

        return Jwts.builder()
            .claims(claims)
            .subject(login)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(
                System.currentTimeMillis() + jwtSecretProperties.accessExpirationDate
            ))
            .signWith(jwtSecretProperties.getSecretKey())
            .compact()
    }

    fun generateRefreshToken(login: String): String {
        return Jwts.builder()
            .subject(login)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + jwtSecretProperties.refreshExpirationDate))
            .signWith(jwtSecretProperties.getSecretKey())
            .compact()
    }

    fun validateToken(token: String): Claims {
        return Jwts.parser()
            .verifyWith(jwtSecretProperties.getSecretKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun extractPermissions(roles: MutableList<Role>): MutableSet<Permission> {
        val permissions: MutableSet<Permission> = mutableSetOf()

        for (role in roles) {
            permissions.addAll(role.permissions);
        }

        return permissions
    }

    fun validateRefreshToken(token: String): User {
        val claims = validateToken(token);

        val refreshToken = refreshTokenRepository.findByToken(token).orElseThrow {
            RefreshTokenNotFoundException(ExceptionMessage.REFRESH_TOKEN_NOT_FOUND);
        };

        val user = userUtils.findUserByLoginAndGet(claims.subject);

        if (refreshToken.user?.id != user.id) {
            throw RefreshTokenIsInvalidException(ExceptionMessage.REFRESH_TOKEN_NOT_FOUND);
        }

        return refreshToken.user ?: throw UserNotFoundException(ExceptionMessage.USER_NOT_FOUND);
    }
}