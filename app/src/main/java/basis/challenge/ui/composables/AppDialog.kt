package basis.challenge.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import basis.challenge.utils.theme.TextType
import basis.challenge.utils.theme.spacingLarge
import basis.challenge.utils.theme.spacingNormal

@Composable
private fun AppDialog(content: @Composable () -> Unit) {
    Dialog(
        onDismissRequest = {},
        properties =
            DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
            ),
        content = {
            Column(
                modifier =
                    Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(spacingNormal),
                        ).padding(spacingLarge),
                verticalArrangement = Arrangement.spacedBy(spacingNormal),
                content = {
                    content()
                },
            )
        },
    )
}

@Composable
fun CreateAddressDialog() {
    AppDialog {
        ConstraintLayout(modifier = Modifier.fillMaxWidth().padding(spacingNormal)) {
            Text(text = "Adicionar endereço", style = TextType.h2)
        }
    }
}
