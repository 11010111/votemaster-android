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
    onSignOutClick: () -> Unit,
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
                onSignOutClick = onSignOutClick
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
        onSignOutClick = {},
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
        onSignOutClick = {},
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
        onSignOutClick = {},
        onRetry = {}
    )
}

