package de.multiplebytes.votemaster.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import de.multiplebytes.votemaster.presentation.theme.VoteMasterTheme

@Composable
fun Failure(
    modifier: Modifier = Modifier,
    title: String = "Error",
    message: String,
    onDismissRequest: () -> Unit,
    onRetry: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Dialog(
            onDismissRequest = onDismissRequest
        ) {
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(text = message)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.End),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = onRetry) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FailurePreview() {
    VoteMasterTheme {
        Failure(
            title = "Error",
            message = "Unknown error",
            onDismissRequest = {},
            onRetry = {}
        )
    }
}
