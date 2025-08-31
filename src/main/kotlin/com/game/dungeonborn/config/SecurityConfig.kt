package com.game.dungeonborn.config

import com.game.dungeonborn.config.filter.JwtAuthFilter
import com.game.dungeonborn.constant.Route
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val jwtAuthFilter: JwtAuthFilter
) {

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        corsConfigurationSource: CorsConfigurationSource
    ): SecurityFilterChain {
        return http
            .cors { it.configurationSource(corsConfigurationSource) }
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/css/**",
                    "/js/**",
                    "/images/**"
                ).permitAll()
                    .requestMatchers(HttpMethod.POST, Route.API_FULL_REGISTRATION_ROUTE).permitAll()
                    .requestMatchers(HttpMethod.POST, Route.API_FULL_LOGIN_ROUTE).permitAll()
                    .requestMatchers(HttpMethod.POST, Route.API_FULL_ADMIN_LOGIN_ROUTE).permitAll()
                    .requestMatchers(HttpMethod.POST, Route.API_FULL_REFRESH_TOKEN_ROUTE).permitAll()
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .anyRequest().authenticated()

            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()

    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder();
    }
}