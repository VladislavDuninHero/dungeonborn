package com.game.dungeonborn.security

import com.game.dungeonborn.entity.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class SecurityUser(
    private val user: User,
): UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return user.roles
            .flatMap { it.permissions }
            .map { SimpleGrantedAuthority(it.authority) }
            .toSet()
    }

    override fun getPassword(): String {
        return user.password;
    }

    override fun getUsername(): String {
        return user.login;
    }
}