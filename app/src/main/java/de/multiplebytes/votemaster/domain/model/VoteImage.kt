package de.multiplebytes.votemaster.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VoteImage(
    val id: Int? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("vote_id")
    val voteId: String,
    @SerialName("image_url")
    val imageUrl: String
)
