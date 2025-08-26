package com.game.dungeonborn.service.role

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.enums.PermissionType
import com.game.dungeonborn.enums.Roles
import com.game.dungeonborn.exception.NotImplementedException
import org.springframework.stereotype.Component

@Component
class RolePermissionsFactory {

    private val rolePermissions: Map<Roles, List<PermissionType>> = mapOf(
        Roles.ADMIN to listOf(
            PermissionType.CREATE_USER,
            PermissionType.READ_USER,
            PermissionType.UPDATE_USER,
            PermissionType.DELETE_USER,
            PermissionType.CREATE_CHAR,
            PermissionType.READ_CHAR,
            PermissionType.UPDATE_CHAR,
            PermissionType.DELETE_CHAR,
            PermissionType.CREATE_DUNGEON,
            PermissionType.READ_DUNGEON,
            PermissionType.UPDATE_DUNGEON,
            PermissionType.DELETE_DUNGEON,
        ),
        Roles.PLAYER to listOf(
            PermissionType.READ_USER,
            PermissionType.UPDATE_USER,
            PermissionType.DELETE_USER,
            PermissionType.CREATE_CHAR,
            PermissionType.READ_CHAR,
            PermissionType.UPDATE_CHAR,
            PermissionType.DELETE_CHAR,
            PermissionType.READ_DUNGEON,
        ),
    );

    public fun getPermissionsForRole(role: Roles): List<PermissionType> {
        return rolePermissions[role] ?: throw NotImplementedException(ExceptionMessage.ROLE_NOT_IMPLEMENTED)
    }
}