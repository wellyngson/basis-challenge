package basis.challenge.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import basis.challenge.utils.theme.GraySecondary

@Composable
fun AppDivider(
    modifier: Modifier = Modifier,
    color: Color = GraySecondary,
    height: Dp = 1.dp,
) {
    HorizontalDivider(
        color = color,
        thickness = height,
        modifier =
            modifier
                .fillMaxWidth()
                .height(height),
    )
}
