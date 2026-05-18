package de.multiplebytes.votemaster.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val id: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    val name: String,
    val biography: String,
    @SerialName("image_url")
    val imageUrl: String = ""
)
