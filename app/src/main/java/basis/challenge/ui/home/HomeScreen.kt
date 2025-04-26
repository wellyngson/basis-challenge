package basis.challenge.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    goToAddUser: () -> Unit,
) {
    val present: HomePresent = koinInject()
    val uiState = present.uiState.collectAsStateWithLifecycle()

    HomeContent(
        modifier = modifier,
        uiState = uiState.value,
        addUser = goToAddUser,
    )
}
