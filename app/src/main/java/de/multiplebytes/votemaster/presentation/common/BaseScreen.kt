package de.multiplebytes.votemaster.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.multiplebytes.votemaster.presentation.feature.credit.CreditIntent
import de.multiplebytes.votemaster.presentation.feature.credit.CreditViewModel
import de.multiplebytes.votemaster.presentation.feature.gift.GiftScreen
import de.multiplebytes.votemaster.presentation.feature.profile.ProfileIntent
import de.multiplebytes.votemaster.presentation.feature.profile.ProfileScreen
import de.multiplebytes.votemaster.presentation.feature.profile.ProfileViewModel
import de.multiplebytes.votemaster.presentation.feature.ranking.RankingScreen
import de.multiplebytes.votemaster.presentation.feature.vote.VoteIntent
import de.multiplebytes.votemaster.presentation.feature.vote.VoteScreen
import de.multiplebytes.votemaster.presentation.feature.vote.VoteViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    onSignOutClick: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val currentTitle = when {
        currentDestination?.hasRoute<BaseRoute.Vote>() == true -> BaseTab.Vote.title
        currentDestination?.hasRoute<BaseRoute.Ranking>() == true -> BaseTab.Ranking.title
        currentDestination?.hasRoute<BaseRoute.Gift>() == true -> BaseTab.Gift.title
        currentDestination?.hasRoute<BaseRoute.Profile>() == true -> BaseTab.Profile.title
        else -> "Vote Master"
    }

    val canNavigateBack = navController.previousBackStackEntry != null
    var selectedTab by rememberSaveable { mutableStateOf(BaseTab.Vote) }

    val creditViewModel: CreditViewModel = koinViewModel()
    val creditUiState by creditViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = currentTitle)
                },
                navigationIcon = {
                    if (canNavigateBack) {
                        IconButton(
                            onClick = {
                                navController.navigateUp()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBackIosNew,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                actions = {
                    if (currentDestination?.hasRoute<BaseRoute.Vote>() == true) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.LocalFireDepartment,
                                contentDescription = "Points",
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                modifier = Modifier.width(44.dp),
                                text = "${creditUiState.credits}",
                                style = MaterialTheme.typography.labelSmall,
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                BaseTab.entries.forEach { tabItem ->
                    NavigationBarItem(
                        selected = selectedTab == tabItem,
                        onClick = { selectedTab = tabItem },
                        icon = {
                            Icon(
                                imageVector = tabItem.icon,
                                contentDescription = tabItem.title
                            )
                        },
                        label = { Text(text = tabItem.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = selectedTab.route
        ) {
            composable<BaseRoute.Vote> {
                val voteViewModel: VoteViewModel = koinViewModel()
                val uiState by voteViewModel.uiState.collectAsStateWithLifecycle()

                VoteScreen(
                    modifier = Modifier.padding(innerPadding),
                    uiState = uiState,
                    onDislike = { id ->
                        voteViewModel.onIntent(
                            intent = VoteIntent.Upvote(id = id)
                        )
                        creditViewModel.onIntent(
                            intent = CreditIntent.Increment(count = 1)
                        )
                    },
                    credits = creditUiState.credits,
                    onGiftClick = {},
                    onLike = { id ->
                        voteViewModel.onIntent(
                            intent = VoteIntent.Upvote(id = id)
                        )
                        creditViewModel.onIntent(
                            intent = CreditIntent.Increment(count = 1)
                        )
                    },
                )
            }
            composable<BaseRoute.Ranking> {
                RankingScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
            composable<BaseRoute.Gift> {
                GiftScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
            composable<BaseRoute.Profile> {
                val profileViewModel: ProfileViewModel = koinViewModel()
                val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()

                ProfileScreen(
                    modifier = Modifier.padding(innerPadding),
                    uiState = uiState,
                    onSignOutClick = onSignOutClick,
                    onRetry = {
                        profileViewModel.onIntent(
                            intent = ProfileIntent.Retry
                        )
                    }
                )
            }
        }
    }
}
