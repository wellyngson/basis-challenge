package basis.challenge.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import basis.challenge.utils.constants.EMPTY_STRING
import basis.challenge.utils.extensions.primaryButtonStyle
import basis.challenge.utils.extensions.transparentButtonBackground
import basis.challenge.utils.theme.TextType
import basis.challenge.utils.theme.mediumButtonStyle
import basis.challenge.utils.theme.spacingNone
import basis.challenge.utils.theme.spacingNormal
import basis.challenge.utils.theme.spacingXLarge

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true,
    backgroundStyle: ButtonColors = primaryButtonStyle(),
    textColor: Color = Color.White,
    @DrawableRes drawable: Int? = null,
) {
    Button(
        onClick = { onClick() },
        colors = backgroundStyle,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = spacingNone),
        enabled = enabled,
        modifier =
            modifier
                .fillMaxWidth()
                .mediumButtonStyle(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacingNormal),
        ) {
            if (drawable != null) {
                Icon(
                    painter = painterResource(id = drawable),
                    tint =
                        when (backgroundStyle) {
                            transparentButtonBackground() ->
                                Color.Black.takeIf { enabled }
                                    ?: Color.Black.copy(alpha = 0.4f)

                            primaryButtonStyle() -> Color.White
                            else -> textColor
                        },
                    contentDescription = EMPTY_STRING,
                    modifier = Modifier.size(spacingXLarge),
                )
            }

            Text(
                text = text,
                style = TextType.button2,
                textAlign = TextAlign.Center,
                color =
                    when (backgroundStyle) {
                        transparentButtonBackground() ->
                            Color.Black.takeIf { enabled }
                                ?: Color.Black.copy(alpha = 0.4f)

                        primaryButtonStyle() -> Color.White
                        else -> textColor
                    },
            )
        }
    }
}
