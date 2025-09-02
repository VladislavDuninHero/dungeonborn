package com.game.dungeonborn.config.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.game.dungeonborn.constant.ExceptionMessage
import com.game.dungeonborn.constant.Route
import com.game.dungeonborn.dto.exception.UserExceptionDTO
import com.game.dungeonborn.enums.ErrorCode
import com.game.dungeonborn.service.security.jwt.JwtService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpMethod.OPTIONS
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    val objectMapper: ObjectMapper,
    val userDetailsService: UserDetailsService,
    private val jwtService: JwtService
): OncePerRequestFilter() {

    private val authWhiteList = arrayOf(
        Route.API_FULL_REGISTRATION_ROUTE,
        Route.API_FULL_LOGIN_ROUTE,
        Route.API_FULL_REFRESH_TOKEN_ROUTE,
        Route.API_FULL_ADMIN_LOGIN_ROUTE
    )
    private val bearerPrefix = "Bearer ";

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (isWhiteListedEndpoint(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        val authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith(bearerPrefix)) {
            val token = authorizationHeader.substring(bearerPrefix.length);

            try {
                val validatedToken = jwtService.validateToken(token);
                val login = validatedToken.subject;

                if (login != null && SecurityContextHolder.getContext().authentication == null) {
                    val userDetails = userDetailsService.loadUserByUsername(login);


                    val authentication = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    );

                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request);

                    SecurityContextHolder.getContext().authentication = authentication;
                }
            } catch (e: Exception) {
                when(e) {
                    is ExpiredJwtException -> {
                        sendError(
                            response,
                            HttpStatus.UNAUTHORIZED.name,
                            HttpStatus.UNAUTHORIZED,
                        )

                        return;
                    }
                    is UsernameNotFoundException -> {
                        sendError(
                            response,
                            ExceptionMessage.USER_NOT_FOUND,
                            HttpStatus.UNAUTHORIZED,
                        )
                        return;
                    }
                    is SignatureException -> {
                        sendError(
                            response,
                            ExceptionMessage.INVALID_TOKEN,
                            HttpStatus.UNAUTHORIZED,
                        )
                        return;
                    }
                    is MalformedJwtException -> {
                        sendError(
                            response,
                            ExceptionMessage.INVALID_TOKEN,
                            HttpStatus.UNAUTHORIZED,
                        )
                        return;
                    }
                }

                sendError(
                    response,
                    e.message ?: ExceptionMessage.SOMETHING_WENT_WRONG,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                )
                return;
            }
        }

        if (authorizationHeader == null) {
            sendError(
                response,
                ExceptionMessage.AUTHORIZATION_HEADER_IS_MISSING,
                HttpStatus.FORBIDDEN
            )
            return;
        }

        filterChain.doFilter(request, response);
    }

    private fun isWhiteListedEndpoint(request: HttpServletRequest): Boolean {
        val requestedUri = request.requestURI;
        return authWhiteList.contains(requestedUri)
                || requestedUri.endsWith(".css")
                || requestedUri.endsWith(".js")
                || request.method == OPTIONS.name()
    }

    private fun sendError(response: HttpServletResponse, message: String, status: HttpStatus) {
        val userExceptionDTO = UserExceptionDTO(
            message,
            ErrorCode.SERVICE_ERROR.name,
        );

        response.status = status.value();
        response.contentType = MediaType.APPLICATION_JSON_VALUE;
        response.writer.write(objectMapper.writeValueAsString(userExceptionDTO));
    }
}