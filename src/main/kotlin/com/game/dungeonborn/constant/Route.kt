package com.game.dungeonborn.constant

object Route {
    const val API_MAIN_ROUTE = "/api/v1"
    const val API_USER_ROUTE = "/api/v1/user"
    const val API_USER_REGISTRATION_ROUTE = "/registration"
    const val API_ADMIN_ROUTE = "/api/v1/admin"
    const val API_ADMIN_USER_CREATE_ROUTE = "/user/create"
    const val API_USER_TOKEN_REFRESH_ROUTE = "/auth/refresh"
    const val API_CHARACTERS_ROUTE = "/api/v1/characters"
    const val API_DUNGEON_ROUTE = "/api/v1/dungeon"

    const val API_FULL_REGISTRATION_ROUTE = "/api/v1/user/registration"
    const val API_FULL_LOGIN_ROUTE = "/api/v1/user/login"
    const val API_FULL_ADMIN_LOGIN_ROUTE = "/api/v1/admin/login"
    const val API_FULL_REFRESH_TOKEN_ROUTE = "/api/v1/user/auth/refresh"

    const val API_LOGIN_ROUTE = "/login"
    const val API_CREATE_ROUTE = "/create"
    const val API_GET_ROUTE = "/{id}"
    const val API_UPDATE_ROUTE = "/update/{id}"
    const val API_DELETE_ROUTE = "/delete/{id}"
    const val API_RECOVERY_ROUTE = "/recovery/{id}"
    const val API_BOOTSTRAP_ROUTE = "/{login}/bootstrap"
    const val API_SELECT_ROUTE = "/select"
    const val API_GET_ALL_ROUTE = "/all"

    const val API_GET_ALL_CHARACTERS_ROUTE = "/{id}/characters"
    const val API_UPDATE_CHARACTER_ROUTE = "/update"
    const val API_CHARACTER_INVENTORY_ADD = "/inventory/add"
    const val API_CHARACTER_INVENTORY_DELETE = "/inventory/delete"
}