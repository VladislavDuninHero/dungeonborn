package com.game.dungeonborn.config.validation

import com.game.dungeonborn.dto.user.UpdateUserDTO
import com.game.dungeonborn.dto.user.UserLoginDTO
import com.game.dungeonborn.dto.user.UserRegistrationDTO
import com.game.dungeonborn.service.validation.base.BaseValidator
import com.game.dungeonborn.service.validation.user.login.validator.LoginSpecialCharsValidator
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration

@Configuration
class ValidationConfig(
    private val userLoginValidators: List<BaseValidator<UserLoginDTO>>,
    private val userUpdateValidators: List<BaseValidator<UpdateUserDTO>>,
) {

    @PostConstruct
    fun configureUserLoginValidators(): List<BaseValidator<UserLoginDTO>> {
        for (i in 1 until userLoginValidators.size - 1) {
            userLoginValidators[i - 1].next = userLoginValidators[i];
        }

        return userLoginValidators;
    }

    @PostConstruct
    fun configureUserUpdateValidators(): List<BaseValidator<UpdateUserDTO>> {
        for (i in 1 until userUpdateValidators.size - 1) {
            userUpdateValidators[i - 1].next = userUpdateValidators[i];
        }

        return userUpdateValidators;
    }
}