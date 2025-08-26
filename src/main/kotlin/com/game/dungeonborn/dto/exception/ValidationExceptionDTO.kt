package com.game.dungeonborn.dto.exception

data class ValidationExceptionDTO<T : AbstractException>(
    val errors: List<T>
)
