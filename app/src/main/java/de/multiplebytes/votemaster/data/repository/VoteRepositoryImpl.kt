package de.multiplebytes.votemaster.data.repository

import de.multiplebytes.votemaster.domain.model.Vote
import de.multiplebytes.votemaster.domain.repository.VoteRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

class VoteRepositoryImpl(
    private val supabaseClient: SupabaseClient
) : VoteRepository {
    private val votesTabelle = "votes"

    @OptIn(SupabaseExperimental::class)
    override fun observe(): Flow<List<Vote>> = supabaseClient.from(votesTabelle)
        .selectAsFlow(Vote::id)
        .distinctUntilChanged()

    override suspend fun create(profileId: String) {
        val userId = supabaseClient.auth.currentUserOrNull()?.id ?: ""

        supabaseClient.from(votesTabelle).insert(
            value = Vote(
                userId = userId,
                profileId = profileId
            )
        )
    }
}
