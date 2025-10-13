package com.game.dungeonborn.service.bootstrap

import com.game.dungeonborn.dto.bootstrap.BootstrapDTO
import com.game.dungeonborn.extensions.character.CharacterMapper
import com.game.dungeonborn.extensions.user.UserMapper
import com.game.dungeonborn.service.security.utils.SecurityUtils
import com.game.dungeonborn.service.utils.user.UserUtils
import org.springframework.stereotype.Service

@Service
class BootstrapService(
    private val userUtils: UserUtils,
    private val characterMapper: CharacterMapper,
    private val userMapper: UserMapper,
    private val securityUtils: SecurityUtils
) {

    fun getBootstrap(userId: Long): BootstrapDTO {
        val foundUser = userUtils.findUserByIdAndGet(userId);

        securityUtils.validateUser(foundUser);

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