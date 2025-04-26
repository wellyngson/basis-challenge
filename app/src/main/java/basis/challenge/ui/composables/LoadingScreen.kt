package basis.challenge.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import basis.challenge.R
import basis.challenge.utils.theme.spacingBig
import basis.challenge.utils.theme.spacingNormal
import basis.challenge.utils.theme.spacingSmall

@Composable
fun LoadingScreen() {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2F)),
        contentAlignment = Alignment.Center,
    ) {
        LocalSoftwareKeyboardController.current?.hide()

        Column(
            modifier =
                Modifier
                    .background(Color.Black.copy(alpha = 0.2F), RoundedCornerShape(spacingSmall))
                    .padding(vertical = spacingNormal, horizontal = spacingBig),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator()
            Text(
                modifier = Modifier.padding(top = spacingSmall),
                text = stringResource(id = R.string.default_wait),
            )
        }
    }
}
