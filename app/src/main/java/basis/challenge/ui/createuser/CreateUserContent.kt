package basis.challenge.ui.createuser

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.constraintlayout.compose.ConstraintLayout
import basis.challenge.R
import basis.challenge.ui.composables.AppButton
import basis.challenge.ui.composables.AppSelectPersonType
import basis.challenge.ui.composables.AppTextField
import basis.challenge.ui.composables.Header
import basis.challenge.utils.constants.EMPTY_STRING
import basis.challenge.utils.enums.PersonTypeEnum
import basis.challenge.utils.extensions.cnpjVisualTransformation
import basis.challenge.utils.extensions.cpfVisualTransformation
import basis.challenge.utils.extensions.isValidCnpj
import basis.challenge.utils.extensions.isValidCpf
import basis.challenge.utils.extensions.isValidEmail
import basis.challenge.utils.extensions.phoneVisualTransformation
import basis.challenge.utils.extensions.transparentButtonBackground
import basis.challenge.utils.theme.spacingNormal

@Composable
fun CreateUserContent(modifier: Modifier = Modifier) {
    val personTypeSelected = remember { mutableStateOf(PersonTypeEnum.INDIVIDUAL) }
    val name = remember { mutableStateOf<String?>(null) }
    val document = remember { mutableStateOf<String?>(null) }
    val phoneNumber = remember { mutableStateOf<String?>(null) }
    val email = remember { mutableStateOf<String?>(null) }
    val maxLengthDocument = if (personTypeSelected.value == PersonTypeEnum.INDIVIDUAL) 11 else 14
    val visualTransformationPhone: VisualTransformation = remember { phoneVisualTransformation() }
    val visualTransformationCpf: VisualTransformation = remember { cpfVisualTransformation() }
    val visualTransformationCnpj: VisualTransformation = remember { cnpjVisualTransformation() }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding(),
    ) {
        Header()

        ConstraintLayout(modifier.fillMaxWidth().padding(spacingNormal)) {
            val (header, personTypeSelector, nameConstraint, documentConstraint, phoneNumberConstraint, emailConstraint, addAddress, createUser) = createRefs()

            AppSelectPersonType(
                modifier =
                    Modifier.constrainAs(personTypeSelector) {
                        top.linkTo(header.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                personTypeSelected = personTypeSelected.value,
            ) { personTypeSelected.value = it }

            AppTextField(
                modifier =
                    Modifier.constrainAs(nameConstraint) {
                        top.linkTo(personTypeSelector.bottom, spacingNormal)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                value = name.value ?: EMPTY_STRING,
                textChanged = { name.value = it },
                maxLines = 1,
                placeholder =
                    if (personTypeSelected.value == PersonTypeEnum.INDIVIDUAL) {
                        "Nome"
                    } else {
                        "Razão social"
                    },
            )

            AppTextField(
                modifier =
                    Modifier.constrainAs(documentConstraint) {
                        top.linkTo(nameConstraint.bottom, spacingNormal)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                value = document.value ?: EMPTY_STRING,
                textChanged = {
                    val onlyDigits = it.filter { char -> char.isDigit() }
                    if (onlyDigits.length <= maxLengthDocument) {
                        document.value = onlyDigits
                    }
                },
                maxLines = 1,
                keyboardType = KeyboardType.Number,
                visualTransformation =
                    if (personTypeSelected.value == PersonTypeEnum.INDIVIDUAL) {
                        visualTransformationCpf
                    } else {
                        visualTransformationCnpj
                    },
                placeholder =
                    if (personTypeSelected.value == PersonTypeEnum.INDIVIDUAL) {
                        "CPF"
                    } else {
                        "CNPJ"
                    },
            )

            AppTextField(
                modifier =
                    Modifier.constrainAs(phoneNumberConstraint) {
                        top.linkTo(documentConstraint.bottom, spacingNormal)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                keyboardType = KeyboardType.Number,
                value = phoneNumber.value ?: EMPTY_STRING,
                textChanged = { phoneNumber.value = it },
                visualTransformation = visualTransformationPhone,
                maxLines = 1,
                placeholder = "Telefone",
            )

            AppTextField(
                modifier =
                    Modifier.constrainAs(emailConstraint) {
                        top.linkTo(phoneNumberConstraint.bottom, spacingNormal)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                value = email.value ?: EMPTY_STRING,
                keyboardType = KeyboardType.Email,
                textChanged = { email.value = it },
                maxLines = 1,
                placeholder = "Email",
            )

            AppButton(
                onClick = {},
                modifier =
                    Modifier.constrainAs(addAddress) {
                        top.linkTo(emailConstraint.bottom, spacingNormal)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                text = "Adicionar endereço",
                drawable = R.drawable.ic_more,
                backgroundStyle = transparentButtonBackground(),
            )
        }

        AppButton(
            onClick = {},
            enabled = (
                name.value?.isNotEmpty() == true &&
                    phoneNumber.value?.isNotEmpty() == true &&
                    email.value?.isValidEmail() == true &&
                    (
                        (personTypeSelected.value == PersonTypeEnum.INDIVIDUAL && document.value?.isValidCpf() == true) ||
                            (personTypeSelected.value == PersonTypeEnum.COMPANY && document.value?.isValidCnpj() == true)
                    )
            ),
            text = "Criar",
            drawable = R.drawable.ic_more,
        )
    }
}
