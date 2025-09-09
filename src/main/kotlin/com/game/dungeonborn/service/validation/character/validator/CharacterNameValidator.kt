package com.game.dungeonborn.service.validation.character.validator

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.dto.character.UpdateCharacterDTO
import com.game.dungeonborn.exception.RequiredFieldException
import com.game.dungeonborn.service.utils.character.CharacterUtils
import com.game.dungeonborn.service.validation.base.BaseValidator
import org.springframework.core.annotation.Order
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
@Order(2)
class CharacterNameValidator(
    private val characterUtils: CharacterUtils
) : BaseValidator<UpdateCharacterDTO>() {
    override fun validate(value: UpdateCharacterDTO): UpdateCharacterDTO {
        val userLogin = SecurityContextHolder.getContext().authentication.name;
        val characterOwner = characterUtils.findCharacterById(value.characterId)
            .user ?: throw RequiredFieldException("User is required");

        if (userLogin != characterOwner.login) {
            characterUtils.validateCharacterName(value.name);
        }

        if (next != null) {
            return validateNext(value);
        }

        return value;
    }
}