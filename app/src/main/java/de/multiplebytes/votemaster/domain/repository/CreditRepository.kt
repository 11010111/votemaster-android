package de.multiplebytes.votemaster.domain.repository

import de.multiplebytes.votemaster.domain.model.Credit
import kotlinx.coroutines.flow.Flow

interface CreditRepository {
    fun credit(): Flow<Credit>
    suspend fun upsert(count: Int)
}
