package de.multiplebytes.votemaster.presentation.feature.auth.component

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.unit.dp
import de.multiplebytes.votemaster.presentation.feature.auth.AuthUiState
import de.multiplebytes.votemaster.presentation.theme.ThemePreview

@Composable
fun SignInContent(
    modifier: Modifier = Modifier,
    uiState: AuthUiState,
    onSignInClick: (String, String) -> Unit,
    onSignUpClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    var emailValue by rememberSaveable { mutableStateOf("") }
    var passwordValue by rememberSaveable { mutableStateOf("") }

    val isValidEmail = remember(emailValue) {
        Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically)
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
        ) {
            TextField(
                value = emailValue,
                onValueChange = { emailValue = it },
                label = { Text(text = "E-Mail") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                ),
                isError = emailValue.isNotEmpty() && !isValidEmail,
                supportingText = {
                    if (emailValue.isNotEmpty() && !isValidEmail) {
                        Text(text = "Invalid E-Mail Address")
                    }
                },
                singleLine = true
            )

            TextField(
                value = passwordValue,
                onValueChange = { passwordValue = it },
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onSignInClick(emailValue, passwordValue) }
                ),
                singleLine = true
            )
        }

        uiState.errorMessage?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = it,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(onClick = { onSignInClick(emailValue, passwordValue) }) {
                Text(text = "Sign in")
            }

            TextButton(onClick = onSignUpClick) {
                Text(text = "Sign up here")
            }
        }
    }
}

@PreviewWrapper(ThemePreview::class)
@Preview(showBackground = true)
@Composable
private fun SignInContentPreview() {
    SignInContent(
        uiState = AuthUiState(
            errorMessage = "Unknown error"
        ),
        onSignInClick = { _, _ -> },
        onSignUpClick = {}
    )
}
