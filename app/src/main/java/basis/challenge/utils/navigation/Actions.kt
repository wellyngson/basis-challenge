package basis.challenge.utils.navigation

import androidx.navigation.NavHostController

class Actions(
    navHostController: NavHostController,
) {
    // Default
    val navigateBack: () -> Unit = {
        navHostController.navigateUp()
    }
}
