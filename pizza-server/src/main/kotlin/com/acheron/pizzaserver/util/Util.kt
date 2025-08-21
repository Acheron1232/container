package com.acheron.pizzaserver.util

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.lang.RuntimeException

data class User(
    val id: Long,
    var username: String,
    var email: String,
    var role: String
)

@Component
class Util(
) {
    fun getCurrentUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication
        assert(authentication is JwtAuthenticationToken)
        val jwtAuthenticationToken = authentication as JwtAuthenticationToken
        val username: String = authentication.name
        val jwtString = jwtAuthenticationToken.token.tokenValue
        val restClient = RestClient.builder().build()
        val user: Map<String, String>? =
            restClient.method(HttpMethod.GET).uri("http://localhost:8080/user/userinfo")
                .header("Authorization", "Bearer $jwtString")
                .accept(MediaType.APPLICATION_JSON).retrieve()
                .body(object : ParameterizedTypeReference<Map<String, String>>() {})
        println(user)
        if (user != null) {
            return User(
                id = requireNotNull(user["id"]?.toLong()) { "id is null" },
                username = requireNotNull(user["username"]) { "name is null" },
                email = requireNotNull(user["email"]) { "email is null" },
                role = requireNotNull(user["role"]) { "role is null" }
            )
        } else {
            throw RuntimeException()
        }
    }
}