package com.game.dungeonborn.entity.user

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "refresh_tokens")
class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "token", nullable = false, unique = true)
    var token: String,
) {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User? = null
}