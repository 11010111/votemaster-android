package de.multiplebytes.votemaster.core.di

import de.multiplebytes.votemaster.BuildConfig
import de.multiplebytes.votemaster.data.repository.AuthRepositoryImpl
import de.multiplebytes.votemaster.data.repository.CreditRepositoryImpl
import de.multiplebytes.votemaster.data.repository.ProfileRepositoryImpl
import de.multiplebytes.votemaster.data.repository.VoteRepositoryImpl
import de.multiplebytes.votemaster.domain.repository.AuthRepository
import de.multiplebytes.votemaster.domain.repository.CreditRepository
import de.multiplebytes.votemaster.domain.repository.ProfileRepository
import de.multiplebytes.votemaster.domain.repository.VoteRepository
import de.multiplebytes.votemaster.domain.usecase.auth.SessionStatusUseCase
import de.multiplebytes.votemaster.domain.usecase.auth.SignInUseCase
import de.multiplebytes.votemaster.domain.usecase.auth.SignOutUseCase
import de.multiplebytes.votemaster.domain.usecase.auth.SignUpUseCase
import de.multiplebytes.votemaster.domain.usecase.credit.CreditUseCase
import de.multiplebytes.votemaster.domain.usecase.credit.UpdateCreditUseCase
import de.multiplebytes.votemaster.domain.usecase.profile.ProfilesUseCase
import de.multiplebytes.votemaster.domain.usecase.profile.SingleProfileUseCase
import de.multiplebytes.votemaster.domain.usecase.vote.CreateVoteUseCase
import de.multiplebytes.votemaster.domain.usecase.vote.VotesUseCase
import de.multiplebytes.votemaster.presentation.feature.auth.AuthViewModel
import de.multiplebytes.votemaster.presentation.feature.credit.CreditViewModel
import de.multiplebytes.votemaster.presentation.feature.vote.VoteViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<SupabaseClient> {
        createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_KEY
        ) {
            install(Auth)
            install(Postgrest)
            install(Realtime)
            install(Storage)
        }
    }

    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }

    single<ProfileRepository> {
        ProfileRepositoryImpl(get())
    }

    single<VoteRepository> {
        VoteRepositoryImpl(get())
    }

    single<CreditRepository> {
        CreditRepositoryImpl(get())
    }

    factory { SessionStatusUseCase(get()) }
    factory { SignInUseCase(get()) }
    factory { SignUpUseCase(get()) }
    factory { SignOutUseCase(get()) }

    factory { VotesUseCase(get()) }
    factory { CreateVoteUseCase(get()) }
    factory { ProfilesUseCase(get()) }
    factory { SingleProfileUseCase(get()) }

    factory { CreditUseCase(get()) }
    factory { UpdateCreditUseCase(get()) }

    viewModelOf(::AuthViewModel)
    viewModelOf(::VoteViewModel)
    viewModelOf(::CreditViewModel)
}
