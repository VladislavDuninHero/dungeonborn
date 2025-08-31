package com.game.dungeonborn.service.user

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.dto.bootstrap.BootstrapDTO
import com.game.dungeonborn.dto.user.*
import com.game.dungeonborn.entity.user.User
import com.game.dungeonborn.enums.Roles
import com.game.dungeonborn.exception.RequiredFieldException
import com.game.dungeonborn.exception.user.UserIsDisabledException
import com.game.dungeonborn.extensions.user.UserMapper
import com.game.dungeonborn.repositories.UserRepository
import com.game.dungeonborn.service.role.RoleService
import com.game.dungeonborn.service.security.jwt.JwtManager
import com.game.dungeonborn.service.utils.user.UserUtils
import com.game.dungeonborn.service.validation.user.login.UserLoginValidationManager
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userMapper: UserMapper,
    private val roleService: RoleService,
    private val userRepository: UserRepository,
    private val userUtils: UserUtils,
    private val userLoginValidationManager: UserLoginValidationManager,
    private val jwtManager: JwtManager,
) {

    fun registration(user: UserRegistrationDTO): UserRegistrationResponseDTO {
        userUtils.findRegisteredUserByLogin(user.login);
        userUtils.findUserByEmail(user.email);

        val newUser : User = userMapper.toEntity(user);

        roleService.createRoleForUser(newUser, Roles.PLAYER);

        val savedUser = userRepository.save(newUser);

        return userMapper.toDTO(savedUser);
    }

    fun login(user: UserLoginDTO): UserLoginResponseDTO {
        val login = user.login ?: throw RequiredFieldException("Login required");
        val foundUser = userUtils.findUserByLoginAndGet(user.login);

        userLoginValidationManager.validate(user);

        val token = jwtManager.createJwtAccessToken(login);
        val refreshToken = jwtManager.createJwtRefreshToken(foundUser);

        return UserLoginResponseDTO(JwtPairDTO(token, refreshToken.token));
    }

    fun createUser(user: UserRegistrationDTO): UserRegistrationResponseDTO {
        userUtils.findRegisteredUserByLogin(user.login);
        userUtils.findUserByEmail(user.email);

        val newUser : User = userMapper.toEntity(user);

        roleService.createRoleForUser(newUser, Roles.PLAYER);

        val savedUser = userRepository.save(newUser);

        return userMapper.toDTO(savedUser);
    }
}