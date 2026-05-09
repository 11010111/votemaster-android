package de.multiplebytes.votemaster.presentation.feature.vote

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import de.multiplebytes.votemaster.domain.model.Vote
import de.multiplebytes.votemaster.presentation.common.component.Failure
import de.multiplebytes.votemaster.presentation.common.component.Loading
import de.multiplebytes.votemaster.presentation.feature.vote.component.VoteSuccess
import de.multiplebytes.votemaster.presentation.theme.ThemePreview

@Composable
fun VoteScreen(
    modifier: Modifier = Modifier,
    uiState: VoteUiState,
    onDislike: () -> Unit,
    onChat: () -> Unit,
    onLike: () -> Unit,
) {
    when (val currentState = uiState.voteStatus) {
        is VoteStatus.Loading -> Loading(modifier = modifier)

        is VoteStatus.Success -> VoteSuccess(
            modifier = modifier,
            vote = currentState.vote,
            onLike = onLike,
            onChat = onChat,
            onDislike = onDislike
        )

        is VoteStatus.Failure -> Failure(
            modifier = modifier,
            title = "Error",
            message = currentState.message,
            onDismissRequest = {},
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
        onDislike = {},
        onChat = {},
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
                vote = Vote(
                    image = "https://picsum.photos/402/878?random=1",
                    label = "Preview",
                    location = "Earth"
                )
            )
        ),
        onDislike = {},
        onChat = {},
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
        onDislike = {},
        onChat = {},
        onLike = {}
    )
}
