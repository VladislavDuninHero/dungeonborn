package com.game.dungeonborn.entity.user

import com.game.dungeonborn.enums.Roles
import jakarta.persistence.*

@Entity
@Table(name = "role")
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    var role: Roles
) {
    constructor() : this(null, Roles.PLAYER);

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    val permissions: MutableSet<Permission> = mutableSetOf()
}