package com.game.dungeonborn.dto.bootstrap

import com.game.dungeonborn.dto.character.CharacterSlimDTO
import com.game.dungeonborn.dto.user.UserDTO

data class BootstrapDTO(
    val user: UserDTO,
    val characters: List<CharacterSlimDTO>,
)
