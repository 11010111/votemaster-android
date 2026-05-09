package de.multiplebytes.votemaster.presentation.common

import kotlinx.serialization.Serializable

sealed interface BaseRoute {
    @Serializable
    data object Vote : BaseRoute

    @Serializable
    data object Ranking : BaseRoute

    @Serializable
    data object Chat : BaseRoute

    @Serializable
    data object Profile : BaseRoute
}
