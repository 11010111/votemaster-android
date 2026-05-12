package de.multiplebytes.votemaster.presentation.feature.auth.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Photo
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import de.multiplebytes.votemaster.presentation.feature.auth.AuthUiState
import de.multiplebytes.votemaster.presentation.theme.ThemePreview

@Composable
fun PhotoStep(
    modifier: Modifier = Modifier,
    uiState: AuthUiState,
    onSignUpClick: (ByteArray) -> Unit
) {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val photoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        selectedImageUri = uri
    }

    val imageBytes by rememberSaveable(selectedImageUri) {
        mutableStateOf(
            selectedImageUri?.let { uri ->
                context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "Pick a photo for your profile.",
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .widthIn(max = 240.dp)
                .aspectRatio(0.75f)
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = MaterialTheme.shapes.large
                )
                .clip(MaterialTheme.shapes.large)
                .clickable {
                    photoLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri != null) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = selectedImageUri,
                    contentDescription = "Profile Photo",
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    modifier = Modifier.size(64.dp),
                    imageVector = Icons.Rounded.Photo,
                    contentDescription = "Nothing selected",
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
        }

        uiState.errorMessage?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = it,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = {
                imageBytes?.let {
                    onSignUpClick(it)
                }
            },
            enabled = selectedImageUri != null
        ) {
            Text(text = "Sign up")
        }
    }
}

@PreviewWrapper(ThemePreview::class)
@Preview(showBackground = true)
@Composable
private fun PhotoStepPreview() {
    PhotoStep(
        uiState = AuthUiState(
            errorMessage = null
        ),
        onSignUpClick = {}
    )
}
