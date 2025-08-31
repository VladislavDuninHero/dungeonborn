package com.game.dungeonborn.service.validation.base

interface Validator<T> {
    fun validate(value: T) : T
}