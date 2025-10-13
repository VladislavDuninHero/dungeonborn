package com.game.dungeonborn.service.security.utils

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.entity.user.User
import com.game.dungeonborn.exception.user.UserIsInvalidException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class SecurityUtils {
    fun validateUser(user: User) {
        val securityUser = SecurityContextHolder.getContext().authentication.name;

        if (user.login != securityUser) {
            throw UserIsInvalidException(ExceptionMessage.USER_IS_INVALID);
        }
    }
}