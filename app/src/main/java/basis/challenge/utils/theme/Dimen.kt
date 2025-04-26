package basis.challenge.utils.theme

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Spacing
val spacingNone = 0.dp
val spacingTinier = 2.dp
val spacingTiny = 4.dp
val spacingSmall = 8.dp
val spacingMiddle = 12.dp
val spacingNormal = 16.dp
val spacingLarge = 20.dp
val spacingXLarge = 24.dp
val spacingXXLarge = 28.dp
val spacingXXXLarge = 32.dp
val spacingXXXXLarge = 40.dp
val spacingBig = 48.dp
val spacingBigger = 56.dp

val dp60 = 60.dp
val dp64 = 64.dp
val dp68 = 68.dp
val dp74 = 74.dp
val dp80 = 80.dp
val dp96 = 96.dp
val dp128 = 128.dp
val dp246 = 246.dp
val dp284 = 284.dp

// FontSize
val sizeNone = 0.sp
val sizeTinier = 2.sp
val sizeTiny = 4.sp
val sizeSmall = 8.sp
val sizeMiddle = 10.sp
val sizeNormal = 12.sp
val sizeLarge = 14.sp
val sizeXLarge = 16.sp
val sizeXXLarge = 18.sp
val sizeXXXLarge = 20.sp
val sizeXXXXLarge = 24.sp
val sizeXXXXXLarge = 28.sp
val sizeXXXXXXLarge = 32.sp

val sp32 = 32.sp
val sp64 = 64.sp
val sp96 = 96.sp

// Alpha and Fractions
const val af0 = 0f
const val af02 = 0.2f
const val af03 = 0.3f
const val af04 = 0.4f
const val af05 = 0.5f
const val af06 = 0.6f
const val af1 = 1f
const val af2 = 2f

// ButtonSize
@Composable
fun Modifier.mediumButtonStyle() = this.height(spacingBig)

@Composable
fun Modifier.bigButtonHeight() = this.height(spacingBigger)
