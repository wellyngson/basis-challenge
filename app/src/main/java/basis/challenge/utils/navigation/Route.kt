package basis.challenge.utils.navigation

sealed class Route(
    val route: String,
) {
    data object CreateOrUpdateUser : Route(CREATE_OR_UPDATE_USER_SCREEN)

    data object Home : Route(HOME_SCREEN)
}
