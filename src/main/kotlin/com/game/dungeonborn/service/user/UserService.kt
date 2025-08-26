package com.game.dungeonborn.service.user

import com.game.dungeonborn.dto.bootstrap.BootstrapDTO
import com.game.dungeonborn.dto.user.UserLoginDTO
import com.game.dungeonborn.dto.user.UserRegistrationDTO
import com.game.dungeonborn.dto.user.UserRegistrationResponseDTO
import org.springframework.stereotype.Service

@Service
class UserService {

    fun registration(user: UserRegistrationDTO): UserRegistrationResponseDTO {


        return UserRegistrationResponseDTO(1, "", "", "");
    }

    fun login(user: UserLoginDTO): BootstrapDTO {
        return BootstrapDTO(UserRegistrationDTO("", "", ""));
    }
}