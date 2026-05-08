package de.multiplebytes.votemaster.presentation.feature.auth.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun PasswordStep(
    modifier: Modifier = Modifier,
    uiState: AuthUiState,
    onSignUpClick: (String) -> Unit
) {
    var passwordValue by rememberSaveable { mutableStateOf("") }
    var confirmValue by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically)
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            imageVector = Icons.Rounded.Password,
            contentDescription = "Password",
            tint = MaterialTheme.colorScheme.secondary
        )

        Text(text = "Enter your password.")

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
        ) {
            TextField(
                value = passwordValue,
                onValueChange = { passwordValue = it },
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                isError = passwordValue.isNotEmpty() && passwordValue.length < 6,
                supportingText = {
                    if (passwordValue.isNotEmpty() && passwordValue.length < 6) {
                        Text(text = "The password must be at least 6 characters long.")
                    }
                },
                singleLine = true
            )

            TextField(
                value = confirmValue,
                onValueChange = { confirmValue = it },
                label = { Text(text = "Confirm password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                isError = passwordValue != confirmValue,
                supportingText = {
                    if (confirmValue.isNotEmpty() && passwordValue != confirmValue) {
                        Text(text = "Passwords do not match.")
                    }
                },
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

        Button(
            onClick = { onSignUpClick(passwordValue) },
            enabled = passwordValue.isNotEmpty() && passwordValue.length >= 6 && passwordValue == confirmValue
        ) {
            Text(text = "Sign up")
        }
    }
}

@PreviewWrapper(ThemePreview::class)
@Preview(showBackground = true)
@Composable
private fun PasswordStepPreview() {
    PasswordStep(
        uiState = AuthUiState(),
        onSignUpClick = {}
    )
}
