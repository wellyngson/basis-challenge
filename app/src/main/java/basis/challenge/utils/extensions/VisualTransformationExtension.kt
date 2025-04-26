package basis.challenge.utils.extensions

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

fun cpfVisualTransformation(): VisualTransformation =
    VisualTransformation { text ->
        val digits = text.text.filter { it.isDigit() }

        val formattedText =
            buildString {
                for (i in digits.indices) {
                    append(digits[i])
                    if (i == 2 || i == 5) append('.')
                    if (i == 8) append('-')
                }
            }

        val offsetMapping =
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    var transformedOffset = offset
                    if (offset > 2) transformedOffset += 1
                    if (offset > 5) transformedOffset += 1
                    if (offset > 8) transformedOffset += 1
                    return transformedOffset.coerceAtMost(formattedText.length)
                }

                override fun transformedToOriginal(offset: Int): Int {
                    var originalOffset = offset
                    if (offset > 3) originalOffset -= 1
                    if (offset > 7) originalOffset -= 1
                    if (offset > 11) originalOffset -= 1
                    return originalOffset.coerceAtMost(digits.length)
                }
            }

        TransformedText(AnnotatedString(formattedText), offsetMapping)
    }

fun cnpjVisualTransformation(): VisualTransformation =
    VisualTransformation { text ->
        val digits = text.text.filter { it.isDigit() }

        val formattedText =
            buildString {
                for (i in digits.indices) {
                    append(digits[i])
                    if (i == 1 || i == 4) append('.')
                    if (i == 7) append('/')
                    if (i == 11) append('-')
                }
            }

        val offsetMapping =
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    var transformedOffset = offset
                    if (offset > 1) transformedOffset += 1
                    if (offset > 4) transformedOffset += 1
                    if (offset > 7) transformedOffset += 1
                    if (offset > 11) transformedOffset += 1
                    return transformedOffset.coerceAtMost(formattedText.length)
                }

                override fun transformedToOriginal(offset: Int): Int {
                    var originalOffset = offset
                    if (offset > 2) originalOffset -= 1
                    if (offset > 6) originalOffset -= 1
                    if (offset > 10) originalOffset -= 1
                    if (offset > 15) originalOffset -= 1
                    return originalOffset.coerceAtMost(digits.length)
                }
            }

        TransformedText(AnnotatedString(formattedText), offsetMapping)
    }

fun phoneVisualTransformation(): VisualTransformation =
    VisualTransformation { text ->
        val digits = text.text.filter { it.isDigit() }

        val formattedText =
            buildString {
                for (i in digits.indices) {
                    when (i) {
                        0 -> append('(')
                        2 -> append(") ")
                        7 -> append('-')
                    }
                    append(digits[i])
                }
            }

        val offsetMapping =
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    var transformedOffset = offset
                    if (offset >= 1) transformedOffset += 1 // '('
                    if (offset >= 2) transformedOffset += 2 // ') '
                    if (offset >= 7) transformedOffset += 1 // '-'
                    return transformedOffset.coerceAtMost(formattedText.length)
                }

                override fun transformedToOriginal(offset: Int): Int {
                    var originalOffset = offset
                    if (offset > 0) originalOffset -= 1
                    if (offset > 3) originalOffset -= 2
                    if (offset > 8) originalOffset -= 1
                    return originalOffset.coerceAtMost(digits.length)
                }
            }

        TransformedText(AnnotatedString(formattedText), offsetMapping)
    }
