package com.game.dungeonborn.service.utils.user

import com.game.dungeonborn.entity.user.User
import com.game.dungeonborn.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserUtils(
    private val userRepository: UserRepository
) {
    fun findUserByLogin(login: String) {
        userRepository.findUserByLogin(login)
    }
}