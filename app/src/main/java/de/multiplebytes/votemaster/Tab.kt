package de.multiplebytes.votemaster

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChatBubble
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ViewCarousel
import androidx.compose.ui.graphics.vector.ImageVector

enum class Tab(
    val title: String,
    val icon: ImageVector,
    val route: Route
) {
    Vote(title = "Voting", icon = Icons.Rounded.ViewCarousel, route = Route.Vote),
    Location(title = "Location", icon = Icons.Rounded.LocationOn, route = Route.Location),
    Chat(title = "Chats", icon = Icons.Rounded.ChatBubble, route = Route.Chat),
    Profile(title = "Profile", icon = Icons.Rounded.Person, route = Route.Profile)
}
