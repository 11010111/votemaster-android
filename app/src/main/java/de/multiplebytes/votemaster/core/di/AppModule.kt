package de.multiplebytes.votemaster.core.di

import de.multiplebytes.votemaster.presentation.feature.vote.VoteViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::VoteViewModel)
}
