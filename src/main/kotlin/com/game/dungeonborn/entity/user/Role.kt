package com.game.dungeonborn.entity.user

import com.game.dungeonborn.enums.Roles
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

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

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    var createdAt: LocalDateTime? = null;

    @Column(name = "updated_at")
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null;
}