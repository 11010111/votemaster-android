package de.multiplebytes.votemaster

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.multiplebytes.votemaster.presentation.common.BaseScreen
import de.multiplebytes.votemaster.presentation.common.component.Loading
import de.multiplebytes.votemaster.presentation.feature.auth.AuthIntent
import de.multiplebytes.votemaster.presentation.feature.auth.AuthScreen
import de.multiplebytes.votemaster.presentation.feature.auth.AuthViewModel
import de.multiplebytes.votemaster.presentation.theme.VoteMasterTheme
import io.github.jan.supabase.auth.status.SessionStatus
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        enableEdgeToEdge()
        setContent {
            VoteMasterTheme {
                val authViewModel: AuthViewModel = koinViewModel()
                val uiState by authViewModel.uiState.collectAsStateWithLifecycle()

                when (uiState.sessionStatus) {
                    is SessionStatus.Initializing -> {
                        Loading()
                    }

                    is SessionStatus.NotAuthenticated -> {
                        AuthScreen(
                            uiState = uiState,
                            onSignInClick = { email, password ->
                                authViewModel.onIntent(
                                    intent = AuthIntent.SignIn(
                                        email = email,
                                        password = password
                                    )
                                )
                            },
                            onSignUpClick = { email, password ->
                                authViewModel.onIntent(
                                    intent = AuthIntent.SignUp(
                                        email = email,
                                        password = password
                                    )
                                )
                            }
                        )
                    }

                    is SessionStatus.Authenticated -> {
                        BaseScreen(
                            onSignOutClick = {
                                authViewModel.onIntent(
                                    intent = AuthIntent.SignOut
                                )
                            }
                        )
                    }

                    is SessionStatus.RefreshFailure -> {}
                }
            }
        }
    }
}
