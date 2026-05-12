package de.multiplebytes.votemaster.presentation.feature.auth

import kotlinx.serialization.Serializable

sealed interface AuthRoute {
    @Serializable
    data object SignIn : AuthRoute

    @Serializable
    data object NameStep : AuthRoute

    @Serializable
    data class EmailStep(val displayName: String) : AuthRoute

    @Serializable
    data class PasswordStep(val displayName: String, val email: String) : AuthRoute

    @Serializable
    data class PhotoStep(val displayName: String, val email: String, val password: String) : AuthRoute
}
