package com.game.dungeonborn.service.validation.user.login.validator

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.dto.user.UserLoginDTO
import com.game.dungeonborn.exception.user.LoginOrPasswordIsInvalidException
import com.game.dungeonborn.service.utils.user.UserUtils
import com.game.dungeonborn.service.validation.base.BaseValidator
import org.springframework.core.annotation.Order
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
@Order(2)
class LoginPasswordNotEqualValidator(
    private val userUtils: UserUtils,
    private val passwordEncoder: BCryptPasswordEncoder
) : BaseValidator<UserLoginDTO>() {
    override fun validate(value: UserLoginDTO): UserLoginDTO {
        val user = userUtils.findUserByLoginAndGet(value.login ?: "");

        if (!passwordEncoder.matches(value.password, user.password)) {
            throw LoginOrPasswordIsInvalidException(ExceptionMessage.LOGIN_OR_PASSWORD_IS_INVALID);
        }

        if (next != null) {
            return validateNext(value);
        }

        return value;
    }
}