package basis.challenge.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import basis.challenge.domain.model.User
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    goToCreateOrUpdateUser: (User?) -> Unit,
) {
    val present: HomePresent = koinInject()
    val uiState = present.uiState.collectAsStateWithLifecycle()

    HomeContent(
        modifier = modifier,
        uiState = uiState.value,
        result = present.result,
        goToCreateOrUpdateUser = goToCreateOrUpdateUser,
        deleteUser = { present.sendIntent(HomeAction.DeleteUser(it)) },
    )
}
