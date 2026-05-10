package de.multiplebytes.votemaster.core.di

import de.multiplebytes.votemaster.BuildConfig
import de.multiplebytes.votemaster.data.local.AppDatabase
import de.multiplebytes.votemaster.data.local.VoteDao
import de.multiplebytes.votemaster.data.repository.AuthRepositoryImpl
import de.multiplebytes.votemaster.data.repository.CreditRepositoryImpl
import de.multiplebytes.votemaster.data.repository.LocalVoteRepositoryImpl
import de.multiplebytes.votemaster.data.repository.VoteRepositoryImpl
import de.multiplebytes.votemaster.domain.repository.AuthRepository
import de.multiplebytes.votemaster.domain.repository.CreditRepository
import de.multiplebytes.votemaster.domain.repository.LocalVoteRepository
import de.multiplebytes.votemaster.domain.repository.VoteRepository
import de.multiplebytes.votemaster.domain.usecase.auth.SessionStatusUseCase
import de.multiplebytes.votemaster.domain.usecase.auth.SignInUseCase
import de.multiplebytes.votemaster.domain.usecase.auth.SignOutUseCase
import de.multiplebytes.votemaster.domain.usecase.auth.SignUpUseCase
import de.multiplebytes.votemaster.domain.usecase.credit.CreditUseCase
import de.multiplebytes.votemaster.domain.usecase.credit.UpdateCreditUseCase
import de.multiplebytes.votemaster.domain.usecase.vote.AllVotesUseCase
import de.multiplebytes.votemaster.domain.usecase.vote.CreateLocalVoteUseCase
import de.multiplebytes.votemaster.domain.usecase.vote.AllLocalVotesUseCase
import de.multiplebytes.votemaster.domain.usecase.vote.SingleVoteUseCase
import de.multiplebytes.votemaster.presentation.feature.auth.AuthViewModel
import de.multiplebytes.votemaster.presentation.feature.credit.CreditViewModel
import de.multiplebytes.votemaster.presentation.feature.vote.VoteViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import org.koin.android.ext.koin.androidContext
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
        }
    }

    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }

    single<VoteRepository> {
        VoteRepositoryImpl(get())
    }

    single<VoteDao> {
        AppDatabase.getDatabase(androidContext()).voteDao()
    }

    single<LocalVoteRepository> {
        LocalVoteRepositoryImpl(get())
    }

    single<CreditRepository> {
        CreditRepositoryImpl(get())
    }

    factory { SessionStatusUseCase(get()) }
    factory { SignInUseCase(get()) }
    factory { SignUpUseCase(get()) }
    factory { SignOutUseCase(get()) }

    factory { AllLocalVotesUseCase(get()) }
    factory { CreateLocalVoteUseCase(get()) }
    factory { AllVotesUseCase(get()) }
    factory { SingleVoteUseCase(get()) }

    factory { CreditUseCase(get()) }
    factory { UpdateCreditUseCase(get()) }

    viewModelOf(::AuthViewModel)
    viewModelOf(::VoteViewModel)
    viewModelOf(::CreditViewModel)
}
