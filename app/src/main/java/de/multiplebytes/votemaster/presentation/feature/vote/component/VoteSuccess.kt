package de.multiplebytes.votemaster.presentation.feature.vote.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material.icons.rounded.CardGiftcard
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import de.multiplebytes.votemaster.domain.model.Profile
import de.multiplebytes.votemaster.presentation.theme.ThemePreview

@Composable
fun VoteSuccess(
    modifier: Modifier = Modifier,
    profile: Profile,
    credits: Int,
    onDislike: (String) -> Unit,
    onGiftClick: () -> Unit,
    onLike: (String) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = MaterialTheme.shapes.extraLarge
                )
                .clip(shape = MaterialTheme.shapes.extraLarge),
            model = profile.imageUrl,
            contentDescription = profile.name,
            contentScale = ContentScale.Crop,
            loading = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            },
            error = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(128.dp),
                        imageVector = Icons.Rounded.BrokenImage,
                        contentDescription = "Error on load",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background.copy(
                            alpha = 0.8f
                        ),
                        shape = MaterialTheme.shapes.extraLarge.copy(
                            all = CornerSize(24.dp)
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = profile.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )

                Text(
                    text = profile.biography,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledIconButton(
                    modifier = Modifier.size(64.dp),
                    onClick = { profile.id?.let { onDislike(it) } },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Dislike"
                    )
                }

                Column(
                    modifier = Modifier.height(112.dp)
                ) {
                    FilledIconButton(
                        modifier = Modifier.size(80.dp),
                        onClick = onGiftClick,
                        enabled = credits >= 5
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Rounded.CardGiftcard,
                            contentDescription = "Giftcard"
                        )
                    }
                }

                FilledIconButton(
                    modifier = Modifier.size(64.dp),
                    onClick = { profile.id?.let { onLike(it) } },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.Rounded.Favorite,
                        contentDescription = "Like"
                    )
                }
            }
        }
    }
}

@PreviewWrapper(ThemePreview::class)
@Preview(showBackground = true)
@Composable
private fun VoteSuccessPreview() {
    VoteSuccess(
        profile = Profile(
            id = "1234",
            name = "Preview",
            biography = "Sub Preview"
        ),
        credits = 25,
        onDislike = {},
        onGiftClick = {},
        onLike = {}
    )
}
