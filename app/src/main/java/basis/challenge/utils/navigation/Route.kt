package basis.challenge.utils.navigation

sealed class Route(
    val route: String,
) {
    data object Home : Route(HOME_SCREEN)
}
