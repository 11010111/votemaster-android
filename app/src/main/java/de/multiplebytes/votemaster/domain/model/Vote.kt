package de.multiplebytes.votemaster.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Vote(
    val id: String,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("display_name")
    val displayName: String,
    val image: String? = null
)
