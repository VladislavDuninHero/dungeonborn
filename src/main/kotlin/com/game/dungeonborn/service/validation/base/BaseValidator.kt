package com.game.dungeonborn.service.validation.base

abstract class BaseValidator<T> : Validator<T> {
    var next: BaseValidator<T>? = null

    protected fun validateNext(value: T): T {
        return next?.validate(value) ?: value
    }
}