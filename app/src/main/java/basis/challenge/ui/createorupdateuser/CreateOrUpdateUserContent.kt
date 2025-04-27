package basis.challenge.ui.createorupdateuser

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import basis.challenge.R
import basis.challenge.domain.model.Address
import basis.challenge.domain.model.Address.Companion.buildAddressName
import basis.challenge.ui.composables.AppButton
import basis.challenge.ui.composables.AppSelectPersonType
import basis.challenge.ui.composables.AppTextField
import basis.challenge.ui.composables.ConfirmDeleteAddress
import basis.challenge.ui.composables.CreateAddressDialog
import basis.challenge.ui.composables.Header
import basis.challenge.utils.constants.EMPTY_STRING
import basis.challenge.utils.enums.PersonTypeEnum
import basis.challenge.utils.extensions.cnpjVisualTransformation
import basis.challenge.utils.extensions.cpfVisualTransformation
import basis.challenge.utils.extensions.hide
import basis.challenge.utils.extensions.isValidCnpj
import basis.challenge.utils.extensions.isValidCpf
import basis.challenge.utils.extensions.isValidEmail
import basis.challenge.utils.extensions.phoneVisualTransformation
import basis.challenge.utils.extensions.show
import basis.challenge.utils.extensions.transparentButtonBackground
import basis.challenge.utils.theme.RedPrimary
import basis.challenge.utils.theme.TextType
import basis.challenge.utils.theme.spacingNormal
import basis.challenge.utils.theme.spacingSmall
import basis.challenge.utils.theme.spacingTiny
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun CreateOrUpdateUserContent(
    modifier: Modifier = Modifier,
    uiState: CreateOrUpdateUserState,
    result: SharedFlow<CreateOrUpdateUserResult>,
    sendIntent: (CreateOrUpdateUserAction) -> Unit,
    goBack: () -> Unit,
) {
    // Region State
    val name = remember { mutableStateOf(EMPTY_STRING) }
    val document = remember { mutableStateOf(EMPTY_STRING) }
    val phoneNumber = remember { mutableStateOf(EMPTY_STRING) }
    val email = remember { mutableStateOf(EMPTY_STRING) }
    val maxLengthDocument = if (uiState.user?.personType == PersonTypeEnum.INDIVIDUAL) 11 else 14
    // Region Visual Transformation
    val visualTransformationPhone: VisualTransformation = remember { phoneVisualTransformation() }
    val visualTransformationCpf: VisualTransformation = remember { cpfVisualTransformation() }
    val visualTransformationCnpj: VisualTransformation = remember { cnpjVisualTransformation() }
    // Region Visibility
    val createAddressVisibility = remember { mutableStateOf(false) }
    val deleteAddressDialog = remember { mutableStateOf(false) }
    // Region Objects
    val addressToDelete = remember { mutableStateOf<Address?>(null) }
    val buttonEnabledCondition = (
        (
            name.value.isNotEmpty() &&
                phoneNumber.value.isNotEmpty() &&
                (email.value.isValidEmail()).takeIf { email.value.isNotEmpty() }
                    ?: true &&
                (
                    (
                        uiState.user?.personType == PersonTypeEnum.INDIVIDUAL && document.value.isValidCpf()
                    ) ||
                        (uiState.user?.personType == PersonTypeEnum.COMPANY && document.value.isValidCnpj())
                )
        ) &&
            uiState.user.addresses.isNotEmpty()
    )

    LazyColumn(
        modifier =
            modifier
                .fillMaxSize()
                .imePadding(),
    ) {
        item {
            Header()
        }

        item {
            Column(modifier = Modifier.padding(horizontal = spacingNormal)) {
                AppSelectPersonType(
                    modifier = Modifier.padding(top = spacingNormal),
                    personTypeSelected = uiState.user?.personType ?: PersonTypeEnum.INDIVIDUAL,
                    personTypeClicked = { sendIntent(CreateOrUpdateUserAction.UpdatePersonType(it)) },
                )

                AppTextField(
                    modifier = Modifier.padding(top = spacingNormal),
                    value = name.value,
                    textChanged = { name.value = it },
                    maxLines = 1,
                    showTitle = true,
                    placeholder =
                        if (uiState.user?.personType == PersonTypeEnum.INDIVIDUAL) {
                            "Nome*"
                        } else {
                            "Razão social*"
                        },
                )

                AppTextField(
                    modifier = Modifier.padding(top = spacingNormal),
                    value = document.value,
                    textChanged = {
                        val onlyDigits = it.filter { char -> char.isDigit() }
                        if (onlyDigits.length <= maxLengthDocument) {
                            document.value = onlyDigits
                        }
                    },
                    maxLines = 1,
                    keyboardType = KeyboardType.Number,
                    visualTransformation =
                        if (uiState.user?.personType == PersonTypeEnum.INDIVIDUAL) {
                            visualTransformationCpf
                        } else {
                            visualTransformationCnpj
                        },
                    showTitle = true,
                    placeholder =
                        if (uiState.user?.personType == PersonTypeEnum.INDIVIDUAL) {
                            "CPF*"
                        } else {
                            "CNPJ*"
                        },
                )

                AppTextField(
                    modifier = Modifier.padding(top = spacingNormal),
                    keyboardType = KeyboardType.Number,
                    value = phoneNumber.value,
                    textChanged = { phoneNumber.value = it },
                    visualTransformation = visualTransformationPhone,
                    maxLines = 1,
                    showTitle = true,
                    placeholder = "Telefone*",
                )

                AppTextField(
                    modifier = Modifier.padding(top = spacingNormal),
                    value = email.value,
                    keyboardType = KeyboardType.Email,
                    textChanged = { email.value = it },
                    maxLines = 1,
                    showTitle = true,
                    placeholder = "Email",
                )

                if (uiState.user?.addresses?.isNotEmpty() == true) {
                    Text(
                        modifier = Modifier.padding(top = spacingNormal),
                        text = "Endereços",
                        style = TextType.h2,
                    )
                }
            }
        }

        items(uiState.user?.addresses ?: emptyList()) { item ->
            AddressItem(
                modifier =
                    Modifier.padding(
                        top = spacingNormal,
                        start = spacingNormal,
                        end = spacingNormal,
                    ),
                address = item,
                onClick = {},
                onDeleteAddress = {
                    addressToDelete.value = item
                    deleteAddressDialog.show()
                },
            )
        }

        item {
            AppButton(
                modifier =
                    modifier.padding(
                        start = spacingNormal,
                        end = spacingNormal,
                        top = spacingNormal,
                    ),
                onClick = { createAddressVisibility.value = true },
                text = "Adicionar endereço",
                drawable = R.drawable.ic_more,
                backgroundStyle = transparentButtonBackground(),
            )
        }

        item {
            AppButton(
                onClick = {
                    val newUser =
                        uiState.user?.copy(
                            name = name.value,
                            document = document.value,
                            phone = phoneNumber.value,
                            email = email.value,
                            personType = uiState.user.personType,
                            addresses = uiState.user.addresses,
                        )

                    newUser?.let {
                        if (uiState.isNewUser) {
                            sendIntent(CreateOrUpdateUserAction.CreateUser(it))
                        } else {
                            sendIntent(CreateOrUpdateUserAction.UpdateUser(it))
                        }
                    }
                },
                enabled = buttonEnabledCondition,
                text =
                    if (uiState.user == null) {
                        "Criar"
                    } else {
                        "Atualizar"
                    },
                modifier =
                    Modifier.padding(
                        start = spacingNormal,
                        end = spacingNormal,
                        top = spacingTiny,
                        bottom = spacingNormal,
                    ),
            )
        }
    }

    if (createAddressVisibility.value) {
        CreateAddressDialog(
            onClose = { createAddressVisibility.value = false },
        ) {
            createAddressVisibility.value = false
            sendIntent(CreateOrUpdateUserAction.AddAddressInUser(it))
        }
    }

    if (deleteAddressDialog.value) {
        ConfirmDeleteAddress(
            onConfirmDeleteAddress = {
                addressToDelete.value?.let {
                    sendIntent(CreateOrUpdateUserAction.RemoveAddressOfUser(it))
                }
                addressToDelete.value = null
                deleteAddressDialog.hide()
            },
            onCancel = {
                addressToDelete.value = null
                deleteAddressDialog.hide()
            },
        )
    }

    LaunchedEffect(uiState.user) {
        name.value = uiState.user?.name.orEmpty()
        document.value = uiState.user?.document.orEmpty()
        phoneNumber.value = uiState.user?.phone.orEmpty()
        email.value = uiState.user?.email.orEmpty()
    }

    LaunchedEffect(result) {
        result.collect {
            when (it) {
                is CreateOrUpdateUserResult.UserUpdatedWithSuccess -> goBack()
                is CreateOrUpdateUserResult.UserCreatedWithSuccess -> goBack()
            }
        }
    }
}

@Composable
private fun AddressItem(
    modifier: Modifier = Modifier,
    address: Address,
    onClick: () -> Unit,
    onDeleteAddress: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(spacingSmall))
                .clickable { onClick() },
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(spacingSmall),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(address.type.icon),
                contentDescription = null,
                tint = RedPrimary,
            )

            Text(
                text = address.buildAddressName(),
                style = TextType.subtitle1,
                modifier =
                    Modifier
                        .padding(horizontal = spacingNormal)
                        .weight(1f),
            )

            Icon(
                painter = painterResource(R.drawable.ic_trash),
                contentDescription = null,
                modifier =
                    Modifier
                        .clip(CircleShape)
                        .clickable { onDeleteAddress() },
            )
        }
    }
}
