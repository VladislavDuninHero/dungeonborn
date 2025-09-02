package com.game.dungeonborn.extensions.user

import com.game.dungeonborn.dto.user.UserDTO
import com.game.dungeonborn.dto.user.UserRegistrationDTO
import com.game.dungeonborn.dto.user.UserRegistrationResponseDTO
import com.game.dungeonborn.entity.user.User
import com.game.dungeonborn.exception.RequiredFieldException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserMapper(
    private val passwordEncoder: BCryptPasswordEncoder,
) {
    fun toEntity(dto: UserRegistrationDTO): User {

        val login = dto.login ?: throw RequiredFieldException("Login is required")
        val password = dto.password ?: throw RequiredFieldException("Password is required")
        val email = dto.email ?: throw RequiredFieldException("Password is required")

        return User(
            login = login,
            password = passwordEncoder.encode(password),
            email = email
        )
    }

    fun toDTO(user: User): UserRegistrationResponseDTO {

        val id = user.id ?: throw RequiredFieldException("ID is required");

        return UserRegistrationResponseDTO(
            id = id,
            login = user.login,
            email = user.email
        )
    }

    fun toUserDTO(user: User): UserDTO {

        val id = user.id ?: throw RequiredFieldException("ID is required");

        return UserDTO(
            id = id,
            login = user.login,
            email = user.email
        )
    }
}