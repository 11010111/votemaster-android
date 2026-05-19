package de.multiplebytes.votemaster.presentation.feature.profile.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import de.multiplebytes.votemaster.domain.model.Profile
import de.multiplebytes.votemaster.presentation.theme.ThemePreview

@Composable
fun ProfileSuccess(
    modifier: Modifier = Modifier,
    profile: Profile,
    onSignOutClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    val scale = remember { Animatable(0.75f) }

    LaunchedEffect(profile.id) {
        scale.snapTo(0.75f)
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .absolutePadding(16.dp, 16.dp, 16.dp, 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.65f)
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                }
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.large
                )
                .clip(MaterialTheme.shapes.large),
            model = profile.imageUrl,
            contentDescription = profile.name,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = profile.name,
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 1
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = profile.biography,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 5
            )
        }

        Button(onClick = onSignOutClick) {
            Text(text = "Sign out")
        }
    }
}

@PreviewWrapper(ThemePreview::class)
@Preview(showBackground = true)
@Composable
private fun ProfileSuccessPreview() {
    ProfileSuccess(
        profile = Profile(
            name = "John Doe",
            biography = "32, New York",
            imageUrl = ""
        ),
        onSignOutClick = {}
    )
}
