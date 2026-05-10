package de.multiplebytes.votemaster.data.repository

import de.multiplebytes.votemaster.domain.model.Credit
import de.multiplebytes.votemaster.domain.repository.CreditRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class CreditRepositoryImpl(
    private val supabaseClient: SupabaseClient
) : CreditRepository {
    private val creditTable = "credits"

    @OptIn(SupabaseExperimental::class)
    override fun credit(): Flow<Credit> {
        val userId = supabaseClient.auth.currentUserOrNull()?.id ?: ""

        return supabaseClient.from(creditTable)
            .selectAsFlow(Credit::id)
            .map { it.firstOrNull() ?: Credit(id = userId, count = 0) }
            .distinctUntilChanged()
    }

    override suspend fun upsert(count: Int) {
        val userId = supabaseClient.auth.currentUserOrNull()?.id ?: ""

        supabaseClient.from(creditTable).upsert(
            value = Credit(
                id = userId,
                count = count
            )
        )
    }
}
