package com.game.dungeonborn.service.validation.user.login


import com.game.dungeonborn.config.validation.ValidationConfig
import com.game.dungeonborn.dto.user.UserLoginDTO
import com.game.dungeonborn.service.validation.base.ValidationManager
import org.springframework.stereotype.Service

@Service
class UserLoginValidationManager(
    private val validationConfig: ValidationConfig
) : ValidationManager<UserLoginDTO> {
    override fun validate(value: UserLoginDTO) {
        validationConfig.configureUserLoginValidators().forEach {it.validate(value) };
    }
}