package com.game.dungeonborn.entity.user

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "login", nullable = false, unique = true, length = 255)
    var login: String = "",

    @Column(name = "password", nullable = false, length = 255)
    var password: String = "",

    @Column(name = "email", nullable = false, unique = true, length = 255)
    var email: String = "",

    @Column(name = "is_enabled", nullable = false)
    var isEnabled: Boolean = true,
) {

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    var roles: MutableList<Role> = mutableListOf()

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    var createdAt: LocalDateTime? = null;

    @Column(name = "updated_at")
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null;
}