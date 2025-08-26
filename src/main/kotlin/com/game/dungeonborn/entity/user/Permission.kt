package com.game.dungeonborn.entity.user

import com.game.dungeonborn.enums.PermissionType
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority

@Entity
@Table(name = "permissions")
class Permission(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "permission_type")
    @Enumerated(EnumType.STRING)
    var permissionType: PermissionType
) : GrantedAuthority {

    constructor() : this(null, PermissionType.READ_USER);

    override fun getAuthority(): String {
        return permissionType.name;
    }

}