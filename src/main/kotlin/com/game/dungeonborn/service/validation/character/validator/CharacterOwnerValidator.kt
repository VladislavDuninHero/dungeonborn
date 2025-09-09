package com.game.dungeonborn.service.validation.character.validator

import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.dto.character.UpdateCharacterDTO
import com.game.dungeonborn.exception.RequiredFieldException
import com.game.dungeonborn.exception.character.UserIsNotCharacterOwnerException
import com.game.dungeonborn.service.utils.character.CharacterUtils
import com.game.dungeonborn.service.validation.base.BaseValidator
import org.springframework.core.annotation.Order
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
@Order(1)
class CharacterOwnerValidator(
    private val characterUtils: CharacterUtils
) : BaseValidator<UpdateCharacterDTO>() {
    override fun validate(value: UpdateCharacterDTO): UpdateCharacterDTO {

        val character = characterUtils.findCharacterById(value.characterId);
        val userLogin = SecurityContextHolder.getContext().authentication.name;
        val ownerCharacter = character.user ?: throw RequiredFieldException("User is required");

        if (ownerCharacter.login != userLogin) {
            throw UserIsNotCharacterOwnerException(String.format(
                ExceptionMessage.USER_IS_NOT_CHARACTER_OWNER, userLogin, character.name
            ))
        }

        if (next != null) {
            return validateNext(value);
        }

        return value;
    }
}