package com.game.dungeonborn.repositories

import com.game.dungeonborn.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<User, Long> {
    fun findUserByLogin(login: String): Optional<User>
    fun findUserByEmail(email: String): Optional<User>
}