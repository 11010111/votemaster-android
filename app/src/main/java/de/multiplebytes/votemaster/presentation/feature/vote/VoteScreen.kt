package de.multiplebytes.votemaster.presentation.feature.vote

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DoneAll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.unit.dp
import de.multiplebytes.votemaster.domain.model.Profile
import de.multiplebytes.votemaster.presentation.common.component.Failure
import de.multiplebytes.votemaster.presentation.common.component.Loading
import de.multiplebytes.votemaster.presentation.feature.vote.component.VoteSuccess
import de.multiplebytes.votemaster.presentation.theme.ThemePreview

@Composable
fun VoteScreen(
    modifier: Modifier = Modifier,
    uiState: VoteUiState,
    credits: Int,
    onDislike: (String) -> Unit,
    onGiftClick: () -> Unit,
    onLike: (String) -> Unit
) {
    when (val currentState = uiState.voteStatus) {
        is VoteStatus.Loading -> Loading(modifier = modifier)

        is VoteStatus.Success -> {
            if (currentState.profile != null) {
                VoteSuccess(
                    modifier = modifier,
                    profile = currentState.profile,
                    credits = credits,
                    onLike = onLike,
                    onGiftClick = onGiftClick,
                    onDislike = onDislike
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        imageVector = Icons.Rounded.DoneAll,
                        contentDescription = "Done",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        is VoteStatus.Failure -> Failure(
            modifier = modifier,
            title = "Error",
            message = currentState.message,
            onRetry = {}
        )
    }
}

@PreviewWrapper(ThemePreview::class)
@Preview(showBackground = true)
@Composable
private fun VoteScreenLoadingPreview() {
    VoteScreen(
        uiState = VoteUiState(
            voteStatus = VoteStatus.Loading
        ),
        credits = 25,
        onDislike = {},
        onGiftClick = {},
        onLike = {}
    )
}

@PreviewWrapper(ThemePreview::class)
@Preview(showBackground = true)
@Composable
private fun VoteScreenSuccessPreview() {
    VoteScreen(
        uiState = VoteUiState(
            voteStatus = VoteStatus.Success(
                profile = Profile(
                    id = "1234",
                    name = "Preview",
                    biography = "Sub Preview",
                )
            )
        ),
        credits = 25,
        onDislike = {},
        onGiftClick = {},
        onLike = {}
    )
}

@PreviewWrapper(ThemePreview::class)
@Preview(showBackground = true)
@Composable
private fun VoteScreenFailurePreview() {
    VoteScreen(
        uiState = VoteUiState(
            voteStatus = VoteStatus.Failure("Unknown error")
        ),
        credits = 25,
        onDislike = {},
        onGiftClick = {},
        onLike = {}
    )
}
