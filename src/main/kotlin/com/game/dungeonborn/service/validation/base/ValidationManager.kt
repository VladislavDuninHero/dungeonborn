package com.game.dungeonborn.service.validation.base

interface ValidationManager<T> {
    fun validate(value: T);
}