package basis.challenge.utils.extensions

import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import basis.challenge.utils.theme.RedPrimary
import basis.challenge.utils.theme.RedSecondary

// Buttons Defaults
@Composable
fun primaryButtonStyle() =
    ButtonDefaults.buttonColors(
        containerColor = RedPrimary,
        disabledContainerColor = RedSecondary,
    )

@Composable
fun transparentButtonBackground() =
    ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
    )

fun setupOpacityOnColor(condition: Boolean): Float = 1f.takeIf { condition } ?: 0.4f
