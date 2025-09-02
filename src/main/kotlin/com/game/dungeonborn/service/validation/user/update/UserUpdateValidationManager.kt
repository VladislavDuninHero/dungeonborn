package com.game.dungeonborn.service.validation.user.update

import com.game.dungeonborn.config.validation.ValidationConfig
import com.game.dungeonborn.dto.user.UpdateUserDTO
import com.game.dungeonborn.service.validation.base.ValidationManager
import org.springframework.stereotype.Service

@Service
class UserUpdateValidationManager(
    private val validationConfig: ValidationConfig
) : ValidationManager<UpdateUserDTO> {
    override fun validate(value: UpdateUserDTO) {
        validationConfig.configureUserUpdateValidators().forEach {it.validate(value) };
    }
}