package com.game.dungeonborn.service.bootstrap

import com.game.dungeonborn.dto.bootstrap.BootstrapDTO
import com.game.dungeonborn.extensions.character.CharacterMapper
import com.game.dungeonborn.extensions.user.UserMapper
import com.game.dungeonborn.service.utils.user.UserUtils
import org.springframework.stereotype.Service

@Service
class BootstrapService(
    private val userUtils: UserUtils,
    private val characterMapper: CharacterMapper,
    private val userMapper: UserMapper,
) {

    fun getBootstrap(login: String): BootstrapDTO {
        val foundUser = userUtils.findUserByLoginAndGet(login);

        val charactersDTO = foundUser.characters.map {
            characterMapper.toCharacterSlimDTO(it)
        }
        val userDTO = userMapper.toUserDTO(foundUser);

        return BootstrapDTO(
            user = userDTO,
            characters = charactersDTO
        )
    }
}