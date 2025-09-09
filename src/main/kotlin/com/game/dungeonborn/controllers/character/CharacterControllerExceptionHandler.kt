package com.game.dungeonborn.controllers.character

import com.game.dungeonborn.dto.exception.CharacterExceptionDTO
import com.game.dungeonborn.dto.exception.UserExceptionDTO
import com.game.dungeonborn.dto.exception.ValidationExceptionDTO
import com.game.dungeonborn.enums.ErrorCode
import com.game.dungeonborn.exception.character.CharacterNameIsTakenException
import com.game.dungeonborn.exception.character.CharacterNotFoundException
import com.game.dungeonborn.exception.character.InventoryIsFullException
import com.game.dungeonborn.exception.character.UserIsNotCharacterOwnerException
import com.game.dungeonborn.exception.user.InvalidEmailException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CharacterControllerExceptionHandler {

    @ExceptionHandler(CharacterNameIsTakenException::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onCharacterNameIsTakenException(ex: CharacterNameIsTakenException): ValidationExceptionDTO<CharacterExceptionDTO> {
        val characterErrorDTO = CharacterExceptionDTO(
            ErrorCode.VALIDATION_ERROR.name,
            ex.localizedMessage
        )

        return ValidationExceptionDTO(listOf(characterErrorDTO))
    }

    @ExceptionHandler(CharacterNotFoundException::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onCharacterNotFoundException(ex: CharacterNotFoundException): ValidationExceptionDTO<CharacterExceptionDTO> {
        val characterErrorDTO = CharacterExceptionDTO(
            ErrorCode.VALIDATION_ERROR.name,
            ex.localizedMessage
        )

        return ValidationExceptionDTO(listOf(characterErrorDTO))
    }

    @ExceptionHandler(InventoryIsFullException::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onCharacterInventoryIsFullException(ex: InventoryIsFullException): ValidationExceptionDTO<CharacterExceptionDTO> {
        val characterErrorDTO = CharacterExceptionDTO(
            ErrorCode.VALIDATION_ERROR.name,
            ex.localizedMessage
        )

        return ValidationExceptionDTO(listOf(characterErrorDTO))
    }

    @ExceptionHandler(UserIsNotCharacterOwnerException::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onUserIsNotCharacterOwnerException(ex: UserIsNotCharacterOwnerException): ValidationExceptionDTO<CharacterExceptionDTO> {
        val characterErrorDTO = CharacterExceptionDTO(
            ErrorCode.VALIDATION_ERROR.name,
            ex.localizedMessage
        )

        return ValidationExceptionDTO(listOf(characterErrorDTO))
    }
}