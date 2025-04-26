package basis.challenge.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import basis.challenge.utils.navigation.Actions
import basis.challenge.utils.navigation.NavGraph
import basis.challenge.utils.theme.BancoDoNordesteTheme
import basis.challenge.utils.theme.GrayPrimary

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BancoDoNordesteTheme {
                val navHostController = rememberNavController()
                val actions =
                    remember(navHostController) { Actions(navHostController = navHostController) }

                Scaffold(
                    containerColor = GrayPrimary,
                ) { _ ->
                    NavGraph(
                        navController = navHostController,
                        actions = actions,
                        activity = this,
                    )
                }
            }
        }
    }
}
