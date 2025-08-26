package com.game.dungeonborn.service.security.detail

import com.game.dungeonborn.repositories.UserRepository
import com.game.dungeonborn.security.SecurityUser
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailServiceImpl(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findUserByLogin(username)
            ?: throw UsernameNotFoundException("User not found: $username")

        return SecurityUser(user)
    }
}