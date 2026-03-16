package de.multiplebytes.votemaster.presentation.feature.location

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.multiplebytes.votemaster.presentation.theme.VoteMasterTheme

@Composable
fun LocationScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Location")
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationScreenPreview() {
    VoteMasterTheme {
        LocationScreen()
    }
}
