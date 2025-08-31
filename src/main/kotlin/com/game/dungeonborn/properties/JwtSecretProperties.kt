package com.game.dungeonborn.properties

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import javax.crypto.SecretKey

@Component
@ConfigurationProperties(prefix = "jwt")
class JwtSecretProperties {
    lateinit var secret: String;
    val accessExpirationDate: Int = 60 * 60 * 1000
    val refreshExpirationDate: Int = 2 * 7 * 24 * 60 * 60 * 1000
    val restorePasswordExpirationDate: Int = 60 * 60 * 24 * 1000

    fun getSecretKey(): SecretKey {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
    }
}