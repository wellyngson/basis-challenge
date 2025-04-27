package basis.challenge.utils.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import basis.challenge.domain.model.User
import basis.challenge.ui.createorupdateuser.CreateOrUpdateUserScreen
import basis.challenge.ui.home.HomeScreen
import basis.challenge.utils.constants.ARGS_USER

@Composable
internal fun NavGraph(
    navController: NavHostController,
    actions: Actions,
    activity: Activity,
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.route,
    ) {
        composable(route = Route.CreateOrUpdateUser.route) {
            val user =
                navController.previousBackStackEntry?.savedStateHandle?.get<User>(ARGS_USER)

            CreateOrUpdateUserScreen(
                user = user,
                goBack = actions.navigateBack,
            )
        }

        composable(route = Route.Home.route) {
            HomeScreen(
                goToCreateOrUpdateUser = actions.goToCreateOrUpdateUser,
            )
        }
    }
}
