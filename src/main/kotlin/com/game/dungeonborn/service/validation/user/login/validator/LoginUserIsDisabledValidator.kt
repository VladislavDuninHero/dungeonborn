package com.game.dungeonborn.service.validation.user.login.validator

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.dto.user.UserLoginDTO
import com.game.dungeonborn.exception.user.UserIsDisabledException
import com.game.dungeonborn.service.utils.user.UserUtils
import com.game.dungeonborn.service.validation.base.BaseValidator
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(1)
class LoginUserIsDisabledValidator(
    private val userUtils: UserUtils
) : BaseValidator<UserLoginDTO>() {
    override fun validate(value: UserLoginDTO): UserLoginDTO {
        val user = userUtils.findUserByLoginAndGet(value.login ?: "");

        if (!user.isEnabled) {
            throw UserIsDisabledException(ExceptionMessage.USER_IS_DISABLED);
        }

        if (next != null) {
            return validateNext(value);
        }

        return value;
    }
}