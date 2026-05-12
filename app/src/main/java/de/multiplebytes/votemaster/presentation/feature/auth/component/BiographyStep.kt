package de.multiplebytes.votemaster.presentation.feature.auth.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ShortText
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.unit.dp
import de.multiplebytes.votemaster.presentation.theme.ThemePreview

@Composable
fun BiographyStep(
    modifier: Modifier = Modifier,
    onNextClick: (String) -> Unit
) {
    var biographyValue by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically)
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            imageVector = Icons.AutoMirrored.Rounded.ShortText,
            contentDescription = "Biography",
            tint = MaterialTheme.colorScheme.secondary
        )

        Text(text = "Enter your biography")

        TextField(
            value = biographyValue,
            onValueChange = {
                if (it.length < 140) {
                    biographyValue = it
                }
            },
            label = { Text(text = "Biography") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            isError = biographyValue.isNotEmpty() && biographyValue.length < 3,
            supportingText = {
                if (biographyValue.isNotEmpty() && biographyValue.length < 3) {
                    Text(text = "The input must be longer than 3 characters.")
                }
            },
            minLines = 4,
            maxLines = 4
        )

        Button(
            onClick = { onNextClick(biographyValue) },
            enabled = !biographyValue.isEmpty() && biographyValue.length > 3
        ) {
            Text(text = "Next")
        }
    }
}

@PreviewWrapper(ThemePreview::class)
@Preview(showBackground = true)
@Composable
private fun BiographyStepPreview() {
    BiographyStep(
        onNextClick = {}
    )
}
