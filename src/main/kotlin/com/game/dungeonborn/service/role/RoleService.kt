package com.game.dungeonborn.service.role

import com.game.dungeonborn.entity.user.Permission
import com.game.dungeonborn.entity.user.Role
import com.game.dungeonborn.entity.user.User
import com.game.dungeonborn.enums.Roles
import com.game.dungeonborn.service.utils.user.UserUtils
import org.springframework.stereotype.Service

@Service
class RoleService(
    private val userUtils: UserUtils,
    private val rolePermissionsFactory: RolePermissionsFactory
) {

    fun createRoleForUser(user: User, role: Roles) {
        val newRole = Role();
        newRole.role = role;

        val permissions : Set<Permission> = rolePermissionsFactory.getPermissionsForRole(role)
            .map { Permission(null, it) }
            .toSet();

        newRole.permissions.addAll(permissions);

        user.roles.add(newRole);
    }

    fun getRoleForUser(login: String): MutableList<Role> {
        val user = userUtils.findUserByLoginAndGet(login);

        return user.roles;
    }
}