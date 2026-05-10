package de.multiplebytes.votemaster.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Credit(
    val id: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    val count: Int
)
