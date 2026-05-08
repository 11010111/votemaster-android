package de.multiplebytes.votemaster.presentation.feature.auth.component

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.unit.dp
import de.multiplebytes.votemaster.presentation.theme.ThemePreview

@Composable
fun EmailStep(
    modifier: Modifier = Modifier,
    onNextClick: (String) -> Unit
) {
    var emailValue by rememberSaveable { mutableStateOf("") }

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
        Icon(
            modifier = Modifier.size(48.dp),
            imageVector = Icons.Rounded.Email,
            contentDescription = "E-Mail",
            tint = MaterialTheme.colorScheme.secondary
        )

        Text(text = "Enter your E-Mail")

        TextField(
            value = emailValue,
            onValueChange = { emailValue = it },
            label = { Text(text = "E-Mail") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            isError = emailValue.isNotEmpty() && !isValidEmail,
            supportingText = {
                if (emailValue.isNotEmpty() && !isValidEmail) {
                    Text(text = "Invalid E-Mail Address")
                }
            },
            singleLine = true
        )

        Button(
            onClick = { onNextClick(emailValue) },
            enabled = !emailValue.isEmpty()
        ) {
            Text(text = "Next")
        }
    }
}

@PreviewWrapper(ThemePreview::class)
@Preview(showBackground = true)
@Composable
private fun EmailStepPreview() {
    EmailStep(
        onNextClick = {}
    )
}
