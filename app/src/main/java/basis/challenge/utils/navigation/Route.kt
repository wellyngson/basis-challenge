package basis.challenge.utils.navigation

sealed class Route(
    val route: String,
) {
    data object CreateUser : Route(CREATE_USER_SCREEN)

    data object Home : Route(HOME_SCREEN)
}
