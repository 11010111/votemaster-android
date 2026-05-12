package de.multiplebytes.votemaster.presentation.feature.auth

import kotlinx.serialization.Serializable

sealed interface AuthRoute {
    @Serializable
    data object SignIn : AuthRoute

    @Serializable
    data object NameStep : AuthRoute

    @Serializable
    data class BiographyStep(val name: String) : AuthRoute

    @Serializable
    data class EmailStep(val name: String, val biography: String) : AuthRoute

    @Serializable
    data class PasswordStep(val name: String, val biography: String, val email: String) : AuthRoute

    @Serializable
    data class PhotoStep(val name: String, val biography: String, val email: String, val password: String) : AuthRoute
}
