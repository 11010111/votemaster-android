package de.multiplebytes.votemaster.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Gift(
    val id: Int? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    val type: Int,
    val sender: String,
    val receiver: String
)
