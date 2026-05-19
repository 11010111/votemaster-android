package de.multiplebytes.votemaster.presentation.feature.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import de.multiplebytes.votemaster.domain.model.Profile
import de.multiplebytes.votemaster.presentation.common.component.Failure
import de.multiplebytes.votemaster.presentation.common.component.Loading
import de.multiplebytes.votemaster.presentation.feature.profile.component.ProfileSuccess
import de.multiplebytes.votemaster.presentation.theme.ThemePreview

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    uiState: ProfileUiState,
    credits: Int,
    onRetry: () -> Unit
) {
    when (val currentStatus = uiState.profileStatus) {
        is ProfileStatus.Loading -> {
            Loading()
        }

        is ProfileStatus.Success -> {
            ProfileSuccess(
                modifier = modifier,
                profile = currentStatus.profile,
                credits = credits
            )
        }

        is ProfileStatus.Failure -> {
            Failure(
                message = currentStatus.message,
                onRetry = onRetry
            )
        }
    }
}

@PreviewWrapper(ThemePreview::class)
@Preview(showBackground = true)
@Composable
private fun ProfileScreenLoadingPreview() {
    ProfileScreen(
        uiState = ProfileUiState(
            profileStatus = ProfileStatus.Loading
        ),
        credits = 100,
        onRetry = {}
    )
}

@PreviewWrapper(ThemePreview::class)
@Preview(showBackground = true)
@Composable
private fun ProfileScreenSuccessPreview() {
    ProfileScreen(
        uiState = ProfileUiState(
            profileStatus = ProfileStatus.Success(
                profile = Profile(
                    name = "John Doe",
                    biography = "32, New York",
                    imageUrl = ""
                )
            )
        ),
        credits = 100,
        onRetry = {}
    )
}

@PreviewWrapper(ThemePreview::class)
@Preview(showBackground = true)
@Composable
private fun ProfileScreenFailurePreview() {
    ProfileScreen(
        uiState = ProfileUiState(
            profileStatus = ProfileStatus.Failure(
                message = "Unknown error"
            )
        ),
        credits = 100,
        onRetry = {}
    )
}

