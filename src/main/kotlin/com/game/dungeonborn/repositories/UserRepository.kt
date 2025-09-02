package com.game.dungeonborn.repositories

import com.game.dungeonborn.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository: JpaRepository<User, Long> {

    @Query("FROM User u JOIN FETCH u.roles r JOIN FETCH r.permissions WHERE u.login = :login")
    fun findUserByLogin(login: String): Optional<User>

    @Query("FROM User u JOIN FETCH u.roles r JOIN FETCH r.permissions WHERE u.email = :email")
    fun findUserByEmail(email: String): Optional<User>

    @Query("FROM User u JOIN FETCH u.roles r JOIN FETCH r.permissions WHERE u.id = :id")
    fun findUserById(id: Long): Optional<User>
}