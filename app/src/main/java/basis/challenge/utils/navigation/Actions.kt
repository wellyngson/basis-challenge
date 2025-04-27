package basis.challenge.utils.navigation

import androidx.navigation.NavHostController
import basis.challenge.domain.model.User
import basis.challenge.utils.constants.ARGS_USER

class Actions(
    navHostController: NavHostController,
) {
    // Default
    val navigateBack: () -> Unit = {
        navHostController.navigateUp()
    }

    // Composables
    val goToCreateOrUpdateUser: (User?) -> Unit = {
        navHostController.currentBackStackEntry?.savedStateHandle?.set(
            ARGS_USER,
            it,
        )

        navHostController.navigate(Route.CreateOrUpdateUser.route)
    }
}
