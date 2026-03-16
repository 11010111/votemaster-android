package de.multiplebytes.votemaster

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.multiplebytes.votemaster.presentation.feature.chat.ChatScreen
import de.multiplebytes.votemaster.presentation.feature.profile.ProfileScreen
import de.multiplebytes.votemaster.presentation.feature.location.LocationScreen
import de.multiplebytes.votemaster.presentation.feature.vote.VoteScreen
import de.multiplebytes.votemaster.presentation.feature.vote.VoteViewModel
import de.multiplebytes.votemaster.presentation.theme.VoteMasterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppStart() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val currentTitle = when (currentRoute) {
        Route.Vote::class.qualifiedName -> Tab.Vote.title
        Route.Location::class.qualifiedName -> Tab.Location.title
        Route.Chat::class.qualifiedName -> Tab.Chat.title
        Route.Profile::class.qualifiedName -> Tab.Profile.title
        else -> "Vote Master"
    }

    val canNavigateBack = navController.previousBackStackEntry != null
    var selectedTab by rememberSaveable { mutableStateOf(Tab.Vote) }

    val voteViewModel: VoteViewModel = viewModel()

    val uiState by voteViewModel.uiState.collectAsStateWithLifecycle()

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
                    if (currentRoute == Route.Vote::class.qualifiedName) {
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
                                text = "${uiState.credits}",
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
                Tab.entries.forEach { tabItem ->
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
            composable<Route.Vote> {
                VoteScreen(
                    modifier = Modifier.padding(innerPadding),
                    voteUiState = uiState.voteUiState,
                    onDislike = {
                        voteViewModel.incrementPoints()
                        voteViewModel.loadVote()
                    },
                    onChat = {},
                    onLike = {
                        voteViewModel.incrementPoints()
                        voteViewModel.loadVote()
                    },
                )
            }
            composable<Route.Location> {
                LocationScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
            composable<Route.Chat> {
                ChatScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
            composable<Route.Profile> {
                ProfileScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Preview
@Composable
private fun AppStartPreview() {
    VoteMasterTheme {
        AppStart()
    }
}
