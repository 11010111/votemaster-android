package de.multiplebytes.votemaster.presentation.feature.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.multiplebytes.votemaster.presentation.theme.VoteMasterTheme

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Chat")
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatScreenPreview() {
    VoteMasterTheme {
        ChatScreen()
    }
}
