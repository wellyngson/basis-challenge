package basis.challenge.utils.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import basis.challenge.ui.createuser.CreateUserScreen
import basis.challenge.ui.home.HomeScreen

@Composable
internal fun NavGraph(
    navController: NavHostController,
    actions: Actions,
    activity: Activity,
) {
    NavHost(
        navController = navController,
        startDestination = Route.CreateUser.route,
    ) {
        composable(route = Route.CreateUser.route) {
            CreateUserScreen()
        }

        composable(route = Route.Home.route) {
            HomeScreen(
                goToCreateUser = actions.goToCreateUser,
            )
        }
    }
}
