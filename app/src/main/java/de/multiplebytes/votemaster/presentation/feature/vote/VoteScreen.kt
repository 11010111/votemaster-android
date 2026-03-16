package de.multiplebytes.votemaster.presentation.feature.vote

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.multiplebytes.votemaster.domain.model.Vote
import de.multiplebytes.votemaster.presentation.common.Failure
import de.multiplebytes.votemaster.presentation.common.Loading
import de.multiplebytes.votemaster.presentation.feature.vote.component.VoteSuccess
import de.multiplebytes.votemaster.presentation.theme.VoteMasterTheme

@Composable
fun VoteScreen(
    modifier: Modifier = Modifier,
    voteUiState: VoteUiState,
    onDislike: () -> Unit,
    onChat: () -> Unit,
    onLike: () -> Unit,
) {
    when (voteUiState) {
        is VoteUiState.Loading -> Loading(modifier = modifier)

        is VoteUiState.Success -> VoteSuccess(
            modifier = modifier,
            vote = voteUiState.vote,
            onLike = onLike,
            onChat = onChat,
            onDislike = onDislike
        )

        is VoteUiState.Failure -> Failure(
            modifier = modifier,
            title = "Error",
            message = voteUiState.message,
            onDismissRequest = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun VoteScreenLoadingPreview() {
    VoteMasterTheme {
        VoteScreen(
            voteUiState = VoteUiState.Loading,
            onDislike = {},
            onChat = {},
            onLike = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun VoteScreenSuccessPreview() {
    VoteMasterTheme {
        VoteScreen(
            voteUiState = VoteUiState.Success(
                vote = Vote(
                    image = "https://picsum.photos/402/878?random=1",
                    label = "Preview",
                    location = "Earth"
                )
            ),
            onDislike = {},
            onChat = {},
            onLike = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun VoteScreenFailurePreview() {
    VoteMasterTheme {
        VoteScreen(
            voteUiState = VoteUiState.Failure("Unknown error"),
            onDislike = {},
            onChat = {},
            onLike = {}
        )
    }
}
