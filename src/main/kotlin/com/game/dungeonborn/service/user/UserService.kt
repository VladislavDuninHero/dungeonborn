package com.game.dungeonborn.service.user

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.dto.bootstrap.BootstrapDTO
import com.game.dungeonborn.dto.character.CharacterDTO
import com.game.dungeonborn.dto.character.UserCharactersDTO
import com.game.dungeonborn.dto.user.*
import com.game.dungeonborn.entity.user.User
import com.game.dungeonborn.enums.Roles
import com.game.dungeonborn.exception.RequiredFieldException
import com.game.dungeonborn.exception.user.UserIsDisabledException
import com.game.dungeonborn.extensions.character.CharacterMapper
import com.game.dungeonborn.extensions.user.UserMapper
import com.game.dungeonborn.repositories.UserRepository
import com.game.dungeonborn.service.role.RoleService
import com.game.dungeonborn.service.security.jwt.JwtManager
import com.game.dungeonborn.service.utils.user.UserUtils
import com.game.dungeonborn.service.validation.user.login.UserLoginValidationManager
import com.game.dungeonborn.service.validation.user.update.UserUpdateValidationManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userMapper: UserMapper,
    private val roleService: RoleService,
    private val userRepository: UserRepository,
    private val userUtils: UserUtils,
    private val userLoginValidationManager: UserLoginValidationManager,
    private val userUpdateValidationManager: UserUpdateValidationManager,
    private val jwtManager: JwtManager,
    private val characterMapper: CharacterMapper,
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

        val jwtPair = JwtPairDTO(token, refreshToken.token);

        return UserLoginResponseDTO(
            userId = foundUser.id ?: 0,
            authorization = jwtPair
        );
    }

    fun getUserById(id: Long): UserDTO {
        val user = userUtils.findUserByIdAndGet(id);

        return userMapper.toUserDTO(user);
    }

    @Transactional
    fun updateUser(id: Long, user: UpdateUserDTO) : UpdateUserResponseDTO {
        val foundUser = userUtils.findUserByIdAndGet(id);

        userUpdateValidationManager.validate(user);

        foundUser.email = user.email;

        val updatedUser = userRepository.save(foundUser);

        return UpdateUserResponseDTO(updatedUser.id, updatedUser.login, updatedUser.email);
    }

    @Transactional
    fun disableUser(id: Long) {
        val user = userUtils.findUserByIdAndGet(id);

        user.isEnabled = false;

        userRepository.save(user);
    }

    @Transactional
    fun recoveryUser(id: Long) {
        val user = userUtils.findUserByIdAndGet(id);

        user.isEnabled = true;

        userRepository.save(user);
    }
}