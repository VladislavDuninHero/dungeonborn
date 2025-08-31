package com.game.dungeonborn.controllers.user

import com.game.dungeonborn.controllers.AbstractExceptionHandler
import com.game.dungeonborn.dto.exception.UserExceptionDTO
import com.game.dungeonborn.dto.exception.ValidationExceptionDTO
import com.game.dungeonborn.enums.ErrorCode
import com.game.dungeonborn.exception.user.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UserControllerExceptionHandler : AbstractExceptionHandler() {

    @ExceptionHandler(UserIsExistsException::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onUserIsExistsException(ex: UserIsExistsException): ValidationExceptionDTO<UserExceptionDTO> {
        val userErrorDTO = UserExceptionDTO(
            ErrorCode.VALIDATION_ERROR.name,
            ex.localizedMessage
        )

        return ValidationExceptionDTO(listOf(userErrorDTO))
    }

    @ExceptionHandler(EmailIsBusyException::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onUserIsExistsException(ex: EmailIsBusyException): ValidationExceptionDTO<UserExceptionDTO> {
        val userErrorDTO = UserExceptionDTO(
            ErrorCode.VALIDATION_ERROR.name,
            ex.localizedMessage
        )

        return ValidationExceptionDTO(listOf(userErrorDTO))
    }

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onUserIsExistsException(ex: UserNotFoundException): ValidationExceptionDTO<UserExceptionDTO> {
        val userErrorDTO = UserExceptionDTO(
            ErrorCode.VALIDATION_ERROR.name,
            ex.localizedMessage
        )

        return ValidationExceptionDTO(listOf(userErrorDTO))
    }

    @ExceptionHandler(LoginOrPasswordIsInvalidException::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onUserIsExistsException(ex: LoginOrPasswordIsInvalidException): ValidationExceptionDTO<UserExceptionDTO> {
        val userErrorDTO = UserExceptionDTO(
            ErrorCode.VALIDATION_ERROR.name,
            ex.localizedMessage
        )

        return ValidationExceptionDTO(listOf(userErrorDTO))
    }

    @ExceptionHandler(InvalidEmailException::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onUserIsExistsException(ex: InvalidEmailException): ValidationExceptionDTO<UserExceptionDTO> {
        val userErrorDTO = UserExceptionDTO(
            ErrorCode.VALIDATION_ERROR.name,
            ex.localizedMessage
        )

        return ValidationExceptionDTO(listOf(userErrorDTO))
    }
}