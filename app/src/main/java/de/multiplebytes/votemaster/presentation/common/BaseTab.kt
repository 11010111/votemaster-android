package de.multiplebytes.votemaster.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChatBubble
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ViewCarousel
import androidx.compose.ui.graphics.vector.ImageVector

enum class BaseTab(
    val title: String,
    val icon: ImageVector,
    val route: BaseRoute
) {
    Vote(title = "Voting", icon = Icons.Rounded.ViewCarousel, route = BaseRoute.Vote),
    Location(title = "Location", icon = Icons.Rounded.LocationOn, route = BaseRoute.Location),
    Chat(title = "Chats", icon = Icons.Rounded.ChatBubble, route = BaseRoute.Chat),
    Profile(title = "Profile", icon = Icons.Rounded.Person, route = BaseRoute.Profile)
}
