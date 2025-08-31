package com.game.dungeonborn.repositories

import com.game.dungeonborn.entity.user.RefreshToken
import com.game.dungeonborn.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun findByToken(token: String): Optional<RefreshToken>
    fun findByUser(user: User): Optional<RefreshToken>
    fun deleteByUser(user: User)
    fun deleteByToken(token: String)
}