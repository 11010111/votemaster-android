package de.multiplebytes.votemaster.presentation.feature.profile.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
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
    credits: Int
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
        Box(
            modifier = Modifier
                .width(240.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.TopEnd
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(240.dp)
                    .graphicsLayer {
                        scaleX = scale.value
                        scaleY = scale.value
                    }
                    .border(
                        width = 8.dp,
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                model = profile.imageUrl,
                contentDescription = profile.name,
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .size(48.dp)
                    .offset(x = -(16.dp))
                    .background(
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.75f),
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.LocalFireDepartment,
                    contentDescription = "Points",
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "$credits",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = profile.name,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.large
                    )
                    .padding(16.dp),
                text = profile.biography,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                minLines = 5,
                maxLines = 5
            )
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
        credits = 123
    )
}
