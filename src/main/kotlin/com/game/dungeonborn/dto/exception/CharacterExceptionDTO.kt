package com.game.dungeonborn.dto.exception

class CharacterExceptionDTO(
    errorMessage: String?,
    errorCode: String
) : AbstractException(
    errorCode,
    errorMessage
)