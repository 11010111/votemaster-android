package de.multiplebytes.votemaster.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CardGiftcard
import androidx.compose.material.icons.rounded.FormatListNumbered
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ViewCarousel
import androidx.compose.ui.graphics.vector.ImageVector

enum class BaseTab(
    val title: String,
    val icon: ImageVector,
    val route: BaseRoute
) {
    Vote(title = "Voting", icon = Icons.Rounded.ViewCarousel, route = BaseRoute.Vote),
    Ranking(title = "Ranking", icon = Icons.Rounded.FormatListNumbered, route = BaseRoute.Ranking),
    Gift(title = "Gifts", icon = Icons.Rounded.CardGiftcard, route = BaseRoute.Gift),
    Profile(title = "Profile", icon = Icons.Rounded.Person, route = BaseRoute.Profile)
}
