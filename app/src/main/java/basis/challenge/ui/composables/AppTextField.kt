package basis.challenge.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import basis.challenge.R
import basis.challenge.utils.constants.EMPTY_STRING
import basis.challenge.utils.extensions.cepVisualTransformation
import basis.challenge.utils.extensions.cnpjVisualTransformation
import basis.challenge.utils.extensions.cpfVisualTransformation
import basis.challenge.utils.extensions.phoneVisualTransformation
import basis.challenge.utils.theme.GrayPrimary
import basis.challenge.utils.theme.RedPrimary
import basis.challenge.utils.theme.TextType
import basis.challenge.utils.theme.spacingNormal
import basis.challenge.utils.theme.spacingSmall
import basis.challenge.utils.theme.spacingTiny

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    textChanged: (String) -> Unit,
    placeholder: String = EMPTY_STRING,
    backgroundColor: Color = GrayPrimary.copy(alpha = 0.3f),
    maxLines: Int = Int.MAX_VALUE,
    imeAction: ImeAction = ImeAction.Default,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isPassword: Boolean = false,
    isFocused: Boolean = false,
    showTitle: Boolean = false,
    onNext: () -> Unit = {},
    onDone: () -> Unit = {},
) {
    val passwordVisible = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val textFieldValue = remember { mutableStateOf(TextFieldValue(text = value)) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(isFocused) {
        if (isFocused) {
            textFieldValue.value =
                textFieldValue.value.copy(
                    selection =
                        TextRange(
                            0,
                            textFieldValue.value.text.length,
                        ),
                )

            focusRequester.requestFocus()
        }
    }

    LaunchedEffect(value) {
        if (textFieldValue.value.text != value) {
            textFieldValue.value = TextFieldValue(text = value)
        }
    }

    Column(modifier.fillMaxWidth()) {
        if (showTitle) {
            Text(
                text = placeholder,
                style = TextType.h4,
                modifier = Modifier.padding(bottom = spacingTiny),
            )
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
            maxLines = maxLines,
            placeholder = {
                Text(text = placeholder, style = TextType.label3)
            },
            colors =
                TextFieldDefaults.colors(
                    cursorColor = RedPrimary,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = RedPrimary,
                    unfocusedIndicatorColor = Color.Black,
                    focusedContainerColor = backgroundColor,
                    unfocusedContainerColor = backgroundColor,
                ),
            shape = RoundedCornerShape(spacingSmall),
            value = textFieldValue.value,
            onValueChange = { newTextFieldValue ->
                val filteredText =
                    when (visualTransformation) {
                        phoneVisualTransformation() -> {
                            newTextFieldValue.text.filter { it.isDigit() }.take(11)
                        }

                        cpfVisualTransformation() -> {
                            newTextFieldValue.text.filter { it.isDigit() }.take(11)
                        }

                        cnpjVisualTransformation() -> {
                            newTextFieldValue.text.filter { it.isDigit() }.take(14)
                        }

                        cepVisualTransformation() -> {
                            newTextFieldValue.text.filter { it.isDigit() }.take(8)
                        }

                        else -> {
                            newTextFieldValue.text
                        }
                    }

                textFieldValue.value = newTextFieldValue.copy(text = filteredText)
                textChanged(filteredText)
            },
            textStyle = TextType.subtitle1,
            visualTransformation =
                PasswordVisualTransformation().takeIf { isPassword && !passwordVisible.value }
                    ?: visualTransformation,
            trailingIcon = {
                if (isPassword) {
                    val icon =
                        if (passwordVisible.value) {
                            R.drawable.ic_eye
                        } else {
                            R.drawable.ic_eye_off
                        }

                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(
                            painter = painterResource(id = icon),
                            modifier = Modifier.size(spacingNormal),
                            tint = RedPrimary,
                            contentDescription = EMPTY_STRING,
                        )
                    }
                }
            },
            keyboardOptions =
                KeyboardOptions.Default.copy(
                    imeAction = imeAction,
                    keyboardType = keyboardType,
                ),
            keyboardActions =
                KeyboardActions(
                    onNext = {
                        onNext()
                        focusManager.moveFocus(FocusDirection.Next)
                    },
                    onDone = {
                        onDone()
                        focusManager.clearFocus()
                    },
                ),
        )
    }
}
