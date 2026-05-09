package de.multiplebytes.votemaster.data.repository

import de.multiplebytes.votemaster.domain.model.Vote
import de.multiplebytes.votemaster.domain.repository.VoteRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow

class VoteRepositoryImpl(
    private val supabaseClient: SupabaseClient
) : VoteRepository {
    private val voteTable = "votes"

    @OptIn(SupabaseExperimental::class)
    override fun votes(): Flow<List<Vote>> {
        return supabaseClient.from(voteTable).selectAsFlow(Vote::id)
    }

    override suspend fun vote(exclude: List<String>): Result<Vote?> = runCatching {
        supabaseClient.from(voteTable).select {
            filter {
                if (exclude.isNotEmpty()) {
                    filterNot(column = "id", operator = FilterOperator.IN, exclude)
                }
            }
            limit(1)
        }.decodeSingleOrNull<Vote>()
    }

    override suspend fun create(vote: Vote): Result<Unit> = runCatching {

    }

    override suspend fun update(vote: Vote): Result<Unit> = runCatching {

    }

    override suspend fun delete(voteId: String): Result<Unit> = runCatching {

    }
}
