package com.game.dungeonborn.service.validation.user.login.validator

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.dto.user.UserLoginDTO
import com.game.dungeonborn.exception.RequiredFieldException
import com.game.dungeonborn.exception.user.LoginOrPasswordIsInvalidException
import com.game.dungeonborn.service.utils.user.UserUtils
import com.game.dungeonborn.service.validation.base.BaseValidator
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(3)
class LoginSpecialCharsValidator(
    private val userUtils: UserUtils
) : BaseValidator<UserLoginDTO>() {
    override fun validate(value: UserLoginDTO): UserLoginDTO {
        val pattern = Regex("^[a-zA-Z0-9]+$");
        val login = value.login ?: throw RequiredFieldException("Field is required");
        val password = value.password ?: throw RequiredFieldException("Field is required");

        if (!pattern.matches(login) && !pattern.matches(password)) {
            throw LoginOrPasswordIsInvalidException(ExceptionMessage.LOGIN_OR_PASSWORD_IS_INVALID);
        }

        if (next != null) {
            return validateNext(value);
        }

        return value;
    }
}