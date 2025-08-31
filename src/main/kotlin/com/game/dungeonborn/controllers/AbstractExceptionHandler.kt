package com.game.dungeonborn.controllers

import com.game.dungeonborn.dto.exception.FieldValidationExceptionDTO
import com.game.dungeonborn.dto.exception.ServiceExceptionDTO
import com.game.dungeonborn.dto.exception.ValidationExceptionDTO
import com.game.dungeonborn.enums.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

abstract class AbstractExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ValidationExceptionDTO<FieldValidationExceptionDTO> {
        val errors: List<FieldValidationExceptionDTO> = ex.bindingResult.fieldErrors
            .map { error: FieldError ->
                FieldValidationExceptionDTO(
                    error.field,
                    error.defaultMessage,
                    ErrorCode.VALIDATION_ERROR.name
                )
            }
            .toList()

        return ValidationExceptionDTO(errors)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun onServiceErrorException(ex: HttpMessageNotReadableException): ValidationExceptionDTO<ServiceExceptionDTO> {
        val userErrorDTO: ServiceExceptionDTO = ServiceExceptionDTO(
            ErrorCode.SERVICE_ERROR.name,
            ex.localizedMessage
        )

        return ValidationExceptionDTO(listOf(userErrorDTO))
    }
}