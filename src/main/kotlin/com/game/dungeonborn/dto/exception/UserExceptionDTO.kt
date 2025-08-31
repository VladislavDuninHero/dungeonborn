package com.game.dungeonborn.dto.exception

class UserExceptionDTO (
    errorMessage: String?,
    errorCode: String
) : AbstractException(
    errorCode,
    errorMessage
)