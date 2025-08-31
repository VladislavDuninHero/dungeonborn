package com.game.dungeonborn.service.security.detail

import com.game.dungeonborn.security.SecurityUser
import com.game.dungeonborn.service.utils.user.UserUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailServiceImpl(
    private val userUtils: UserUtils,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userUtils.findUserByLoginAndGet(username)

        return SecurityUser(user)
    }
}