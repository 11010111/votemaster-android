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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material.icons.rounded.ChatBubble
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import de.multiplebytes.votemaster.domain.model.Vote
import de.multiplebytes.votemaster.presentation.theme.VoteMasterTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun VoteSuccess(
    modifier: Modifier = Modifier,
    vote: Vote,
    onDislike: () -> Unit,
    onChat: () -> Unit,
    onLike: () -> Unit,
) {
    var loading by rememberSaveable { mutableStateOf(false) }

    val roundedCorner = RoundedCornerShape(32.dp)

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        if (vote.image.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surface,
                                MaterialTheme.colorScheme.surfaceContainer
                            )
                        ),
                        shape = roundedCorner
                    )
                    .clip(shape = roundedCorner),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(128.dp),
                    imageVector = Icons.Rounded.Image,
                    contentDescription = "No Profile Image",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = roundedCorner
                    )
                    .clip(shape = roundedCorner),
                model = vote.image,
                contentDescription = vote.label,
                contentScale = ContentScale.Crop,
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingIndicator(modifier = Modifier.size(44.dp))
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
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                },
                onLoading = { loading = false },
                onSuccess = { loading = true }
            )
        }

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
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = vote.label,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )

                Text(
                    text = vote.location,
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
                    onClick = onDislike,
                    enabled = loading,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = "Dislike"
                    )
                }

                Column(
                    modifier = Modifier.height(112.dp)
                ) {
                    FilledIconButton(
                        modifier = Modifier.size(80.dp),
                        onClick = onChat,
                        enabled = loading
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Rounded.ChatBubble,
                            contentDescription = "Dislike"
                        )
                    }
                }

                FilledIconButton(
                    modifier = Modifier.size(64.dp),
                    onClick = onLike,
                    enabled = loading,
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

@Preview(showBackground = true)
@Composable
private fun VoteSuccessPreview() {
    VoteMasterTheme {
        VoteSuccess(
            vote = Vote(
                image = "https://picsum.photos/402/878?random=1",
                label = "Preview",
                location = "Earth"
            ),
            onDislike = {},
            onChat = {},
            onLike = {}
        )
    }
}
