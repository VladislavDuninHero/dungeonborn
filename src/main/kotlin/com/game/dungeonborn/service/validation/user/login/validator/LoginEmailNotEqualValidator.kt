package com.game.dungeonborn.service.validation.user.login.validator

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.dto.user.UserLoginDTO
import com.game.dungeonborn.exception.user.InvalidEmailException
import com.game.dungeonborn.service.utils.user.UserUtils
import com.game.dungeonborn.service.validation.base.BaseValidator
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(2)
class LoginEmailNotEqualValidator(
    private val userUtils: UserUtils
) : BaseValidator<UserLoginDTO>() {
    override fun validate(value: UserLoginDTO): UserLoginDTO {
        val user = userUtils.findUserByLoginAndGet(value.login ?: "");

        val email = user.email;

        if (email != value.email) {
            throw InvalidEmailException(ExceptionMessage.EMAIL_IS_INVALID)
        }

        if (next != null) {
            return validateNext(value);
        }

        return value;
    }
}