package de.multiplebytes.votemaster.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("votes")
data class Vote(
    @PrimaryKey
    val id: String
)
