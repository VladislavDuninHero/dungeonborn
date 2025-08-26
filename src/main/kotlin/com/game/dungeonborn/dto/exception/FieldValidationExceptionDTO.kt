package com.game.dungeonborn.dto.exception

class FieldValidationExceptionDTO(
    val field: String,
    errorMessage: String?,
    errorCode: String
) : AbstractException(
    errorCode,
    errorMessage
)
