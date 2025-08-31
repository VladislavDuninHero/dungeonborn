package com.game.dungeonborn.service.utils.user

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.dto.user.UserLoginDTO
import com.game.dungeonborn.entity.user.User
import com.game.dungeonborn.exception.RequiredFieldException
import com.game.dungeonborn.exception.user.EmailIsBusyException
import com.game.dungeonborn.exception.user.UserIsExistsException
import com.game.dungeonborn.exception.user.UserNotFoundException
import com.game.dungeonborn.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserUtils(
    private val userRepository: UserRepository
) {

    fun findRegisteredUserByLogin(login: String?) {
        val loginField = login ?: throw RequiredFieldException("Required field 'login' is required");

        val registeredUserIsExists = userRepository.findUserByLogin(loginField).isPresent;

        if (registeredUserIsExists) {
            throw UserIsExistsException(ExceptionMessage.USER_IS_EXISTS);
        }
    }

    fun findUserByLoginAndGet(login: String): User {
        return userRepository.findUserByLogin(login)
            .orElseThrow { UserNotFoundException(ExceptionMessage.USER_NOT_FOUND) }
    }

    fun findUserByEmail(email: String?) {
        val emailField = email ?: throw RequiredFieldException("Required field 'email' is required");

        val foundedUserByEmail = userRepository.findUserByEmail(emailField).isPresent;

        if (foundedUserByEmail) {
            throw EmailIsBusyException(ExceptionMessage.EMAIL_IS_BUSY);
        }
    }

    fun validateUserData(user: UserLoginDTO, foundUser: User) {
        val userLogin = user.login;
        val userPassword = user.password;
        val userEmail = user.email;


    }
}