package de.multiplebytes.votemaster

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                            onSignUpClick = { name, biography, email, password, photo ->
                                authViewModel.onIntent(
                                    intent = AuthIntent.SignUp(
                                        name = name,
                                        biography = biography,
                                        email = email,
                                        password = password,
                                        photo = photo
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

                    is SessionStatus.RefreshFailure -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Connection failed",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}
