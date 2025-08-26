package com.game.dungeonborn.dto.exception

class ServiceExceptionDTO(
    errorMessage: String?,
    errorCode: String
) : AbstractException(
    errorCode,
    errorMessage
)
