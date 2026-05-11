package de.multiplebytes.votemaster.data.repository

import de.multiplebytes.votemaster.domain.model.Vote
import de.multiplebytes.votemaster.domain.repository.VoteRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class VoteRepositoryImpl(
    private val supabaseClient: SupabaseClient
) : VoteRepository {
    private val voteTable = "votes"

    @OptIn(SupabaseExperimental::class)
    override fun votes(): Flow<List<Vote>> {
        val userId = supabaseClient.auth.currentUserOrNull()?.id

        return supabaseClient.from(voteTable).selectAsFlow(Vote::id).map { votes ->
            votes.filter { it.id != userId }
        }
    }

    override suspend fun vote(exclude: List<String>): Result<Vote?> = runCatching {
        val userId = supabaseClient.auth.currentUserOrNull()?.id
        val allExclude = if (userId != null) exclude + userId else exclude

        supabaseClient.from(voteTable).select {
            filter {
                if (allExclude.isNotEmpty()) {
                    filterNot(column = "id", operator = FilterOperator.IN, allExclude)
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
