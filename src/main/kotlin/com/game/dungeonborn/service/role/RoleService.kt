package com.game.dungeonborn.service.role

import com.game.dungeonborn.entity.user.Permission
import com.game.dungeonborn.entity.user.Role
import com.game.dungeonborn.entity.user.User
import com.game.dungeonborn.enums.Roles
import com.game.dungeonborn.repositories.UserRepository
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class RoleService(
    private val userRepository: UserRepository,
    private val rolePermissionsFactory: RolePermissionsFactory
) {

    fun createRoleForUser(user: User, role: Roles) {
        val newRole: Role = Role();
        newRole.role = role;

        val permissions : Set<Permission> = rolePermissionsFactory.getPermissionsForRole(role)
            .map { Permission() }
            .toSet();

        newRole.permissions.addAll(permissions);

        user.roles.add(newRole);
    }
}