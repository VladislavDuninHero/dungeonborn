package com.game.dungeonborn.service.validation.character

import com.game.dungeonborn.config.validation.ValidationConfig
import com.game.dungeonborn.dto.character.UpdateCharacterDTO
import com.game.dungeonborn.service.validation.base.ValidationManager
import org.springframework.stereotype.Service

@Service
class UpdateCharacterValidationManager(
    private val validationConfig: ValidationConfig
) : ValidationManager<UpdateCharacterDTO> {
    override fun validate(value: UpdateCharacterDTO) {
        validationConfig.configureUpdateCharacterValidators().forEach {it.validate(value) };
    }
}