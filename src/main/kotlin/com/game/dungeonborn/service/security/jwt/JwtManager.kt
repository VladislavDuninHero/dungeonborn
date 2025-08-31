package com.game.dungeonborn.service.security.jwt

import com.game.dungeonborn.entity.user.RefreshToken
import com.game.dungeonborn.entity.user.User
import com.game.dungeonborn.repositories.RefreshTokenRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtManager(
    private val jwtService: JwtService,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    fun createJwtAccessToken(login: String): String {
        return jwtService.generateAccessToken(login);
    }

    fun createJwtRefreshToken(user: User): RefreshToken {
        refreshTokenRepository.findByUser(user).ifPresent {
            refreshTokenRepository.delete(it)
        };

        val generatedRefreshToken = jwtService.generateRefreshToken(user.login);

        val refreshToken = RefreshToken(token = generatedRefreshToken).apply {
            this.user = user;
        };

        return refreshTokenRepository.save(refreshToken);
    }
}