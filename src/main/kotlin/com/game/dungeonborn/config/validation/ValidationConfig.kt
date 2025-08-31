package com.game.dungeonborn.config.validation

import com.game.dungeonborn.dto.user.UserLoginDTO
import com.game.dungeonborn.dto.user.UserRegistrationDTO
import com.game.dungeonborn.service.validation.base.BaseValidator
import com.game.dungeonborn.service.validation.user.login.validator.LoginSpecialCharsValidator
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration

@Configuration
class ValidationConfig(
    private val userLoginValidators: List<BaseValidator<UserLoginDTO>>,
) {

    @PostConstruct
    fun configureUserLoginValidators(): List<BaseValidator<UserLoginDTO>> {
        for (i in 1 until userLoginValidators.size - 1) {
            userLoginValidators[i - 1].next = userLoginValidators[i];
        }

        return userLoginValidators;
    }
}