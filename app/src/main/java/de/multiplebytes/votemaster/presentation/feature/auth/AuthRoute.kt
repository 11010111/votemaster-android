package de.multiplebytes.votemaster.presentation.feature.auth

import kotlinx.serialization.Serializable

sealed interface AuthRoute {
    @Serializable
    data object SignIn : AuthRoute

    @Serializable
    data object EmailStep : AuthRoute

    @Serializable
    data class PasswordStep(val email: String) : AuthRoute
}
