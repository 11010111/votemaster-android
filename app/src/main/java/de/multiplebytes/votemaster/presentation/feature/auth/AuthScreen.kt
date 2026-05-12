package de.multiplebytes.votemaster.presentation.feature.auth

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import de.multiplebytes.votemaster.presentation.feature.auth.component.BiographyStep
import de.multiplebytes.votemaster.presentation.feature.auth.component.SignIn
import de.multiplebytes.votemaster.presentation.feature.auth.component.EmailStep
import de.multiplebytes.votemaster.presentation.feature.auth.component.NameStep
import de.multiplebytes.votemaster.presentation.feature.auth.component.PasswordStep
import de.multiplebytes.votemaster.presentation.feature.auth.component.PhotoStep
import de.multiplebytes.votemaster.presentation.theme.ThemePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    uiState: AuthUiState,
    onSignInClick: (String, String) -> Unit,
    onSignUpClick: (String, String, String, String, ByteArray) -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val canNavigateBack = navController.previousBackStackEntry != null

    val title = when {
        currentDestination?.hasRoute<AuthRoute.SignIn>() == true -> "Sign In"
        else -> "Sign Up"
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = title) },
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
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = AuthRoute.SignIn
        ) {
            composable<AuthRoute.SignIn> {
                SignIn(
                    uiState = uiState,
                    onSignInClick = onSignInClick,
                    onSignUpClick = {
                        navController.navigate(route = AuthRoute.NameStep)
                    }
                )
            }
            composable<AuthRoute.NameStep> {
                NameStep(
                    onNextClick = { name ->
                        navController.navigate(
                            route = AuthRoute.BiographyStep(
                                name = name
                            )
                        )
                    }
                )
            }
            composable<AuthRoute.BiographyStep> { navBackStackEntry ->
                val route: AuthRoute.BiographyStep = navBackStackEntry.toRoute()

                BiographyStep(
                    onNextClick = { biography ->
                        navController.navigate(
                            route = AuthRoute.EmailStep(
                                name = route.name,
                                biography = biography
                            )
                        )
                    }
                )
            }
            composable<AuthRoute.EmailStep> { navBackStackEntry ->
                val route: AuthRoute.EmailStep = navBackStackEntry.toRoute()

                EmailStep(
                    onNextClick = { email ->
                        navController.navigate(
                            route = AuthRoute.PasswordStep(
                                name = route.name,
                                biography = route.biography,
                                email = email
                            )
                        )
                    }
                )
            }
            composable<AuthRoute.PasswordStep> { navBackStackEntry ->
                val route: AuthRoute.PasswordStep = navBackStackEntry.toRoute()

                PasswordStep(
                    uiState = uiState,
                    onNextClick = { password ->
                        navController.navigate(
                            route = AuthRoute.PhotoStep(
                                name = route.name,
                                biography = route.biography,
                                email = route.email,
                                password = password
                            )
                        )
                    }
                )
            }
            composable<AuthRoute.PhotoStep> { navBackStackEntry ->
                val route: AuthRoute.PhotoStep = navBackStackEntry.toRoute()

                PhotoStep(
                    uiState = uiState,
                    onSignUpClick = { photo ->
                        onSignUpClick(
                            route.name,
                            route.biography,
                            route.email,
                            route.password,
                            photo
                        )
                    }
                )
            }
        }
    }
}

@PreviewWrapper(ThemePreview::class)
@Preview(showBackground = true)
@Composable
private fun AuthScreenPreview() {
    AuthScreen(
        uiState = AuthUiState(),
        onSignInClick = { _, _ -> },
        onSignUpClick = { _, _, _, _, _ -> }
    )
}
