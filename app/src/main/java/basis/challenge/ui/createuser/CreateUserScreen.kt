package basis.challenge.ui.createuser

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject

@Composable
fun CreateUserScreen(modifier: Modifier = Modifier) {
    val present: CreateUserPresent = koinInject()

    CreateUserContent(
        modifier = modifier,
    )
}
