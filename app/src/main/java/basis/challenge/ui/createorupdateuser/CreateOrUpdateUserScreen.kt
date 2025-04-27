@file:OptIn(ExperimentalMaterial3Api::class)

package basis.challenge.ui.createorupdateuser

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import basis.challenge.domain.model.User
import org.koin.compose.koinInject

@Composable
fun CreateOrUpdateUserScreen(
    modifier: Modifier = Modifier,
    goBack: () -> Unit = {},
    user: User?,
) {
    val present: CreateOrUpdateUserPresent = koinInject()
    val uiState = present.uiState.collectAsStateWithLifecycle()

    CreateOrUpdateUserContent(
        modifier = modifier,
        uiState = uiState.value,
        result = present.result,
        sendIntent = { action -> present.sendIntent(action) },
        goBack = goBack,
    )

    LaunchedEffect(user) {
        present.sendIntent(
            CreateOrUpdateUserAction.InitializeUser(
                user = user ?: User.initializeEmptyUser(),
                isNewUser = user == null,
            ),
        )
    }
}
