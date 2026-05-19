package de.multiplebytes.votemaster.data.repository

import de.multiplebytes.votemaster.domain.model.Profile
import de.multiplebytes.votemaster.domain.repository.ProfileRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileRepositoryImpl(
    private val supabaseClient: SupabaseClient
) : ProfileRepository {
    private val profilesTable = "profiles"

    @OptIn(SupabaseExperimental::class)
    override fun observe(): Flow<List<Profile>> {
        val userId = supabaseClient.auth.currentUserOrNull()?.id

        return supabaseClient.from(profilesTable)
            .selectAsFlow(Profile::id)
            .map { profiles ->
                profiles.filter {
                    it.id != userId
                }
            }
    }

    override suspend fun select(exclude: List<String>): Result<Profile?> = runCatching {
        val userId = supabaseClient.auth.currentUserOrNull()?.id
        val allExclude = if (userId != null) exclude + userId else exclude

        supabaseClient.from(profilesTable).select {
            filter {
                if (allExclude.isNotEmpty()) {
                    filterNot(column = "id", operator = FilterOperator.IN, allExclude)
                }
            }
        }.decodeList<Profile>().randomOrNull()
    }

    override suspend fun selectUserProfile(): Result<Profile?> = runCatching {
        val userId = supabaseClient.auth.currentUserOrNull()?.id ?: ""

        supabaseClient.from(profilesTable).select {
            filter { eq("id", userId) }
        }.decodeSingleOrNull()
    }

    override suspend fun create(profile: Profile): Result<Unit> = runCatching {

    }

    override suspend fun update(profile: Profile): Result<Unit> = runCatching {

    }

    override suspend fun delete(userId: String): Result<Unit> = runCatching {

    }
}
