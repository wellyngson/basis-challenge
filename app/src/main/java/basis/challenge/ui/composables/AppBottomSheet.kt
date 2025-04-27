@file:OptIn(ExperimentalMaterial3Api::class)

package basis.challenge.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import basis.challenge.utils.theme.GraySecondary
import basis.challenge.utils.theme.dp64
import basis.challenge.utils.theme.spacingLarge
import basis.challenge.utils.theme.spacingSmall
import basis.challenge.utils.theme.spacingTiny

@Composable
fun AppBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismissRequest: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        scrimColor = GraySecondary.copy(alpha = 0.8f),
        dragHandle = { },
        containerColor = Color.Transparent,
    ) {
        Column(
            Modifier
                .background(
                    Color.White,
                ).padding(
                    bottom = spacingLarge,
                    start = spacingLarge,
                    end = spacingLarge,
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ToucheTimeDragHandle()
            content()
        }
    }
}

@Composable
private fun ToucheTimeDragHandle() {
    Surface(
        modifier = Modifier.padding(top = spacingSmall, bottom = spacingSmall),
        color = GraySecondary,
        shape = RoundedCornerShape(spacingTiny),
    ) {
        Box(
            Modifier
                .size(
                    width = dp64,
                    height = spacingTiny,
                ),
        )
    }
}
