package de.multiplebytes.votemaster

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Vote : Route

    @Serializable
    data object Location : Route

    @Serializable
    data object Chat : Route

    @Serializable
    data object Profile : Route
}
