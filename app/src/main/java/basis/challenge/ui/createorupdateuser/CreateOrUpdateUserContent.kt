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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import basis.challenge.R
import basis.challenge.domain.model.Address
import basis.challenge.domain.model.Address.Companion.buildAddressName
import basis.challenge.ui.composables.AppBottomSheet
import basis.challenge.ui.composables.AppButton
import basis.challenge.ui.composables.AppSelectAddressType
import basis.challenge.ui.composables.AppSelectPersonType
import basis.challenge.ui.composables.AppTextField
import basis.challenge.ui.composables.ConfirmDeleteAddress
import basis.challenge.ui.composables.Header
import basis.challenge.utils.constants.EMPTY_STRING
import basis.challenge.utils.enums.AddressTypeEnum
import basis.challenge.utils.enums.PersonTypeEnum
import basis.challenge.utils.extensions.cepVisualTransformation
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
import kotlinx.coroutines.launch
import org.mongodb.kbson.BsonObjectId

@ExperimentalMaterial3Api
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
    val createAddressBottomSheet = rememberModalBottomSheetState(true)
    val deleteAddressDialog = remember { mutableStateOf(false) }
    // Region Objects
    val addressSelected = remember { mutableStateOf<Address?>(null) }
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
    val coroutineScope = rememberCoroutineScope()
    val openCreateAddressBottomSheet = {
        coroutineScope.launch {
            createAddressBottomSheet.show()
        }
    }
    val closeCreateAddressBottomSheet = {
        coroutineScope.launch {
            createAddressBottomSheet.hide()
        }
    }

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
                    textChanged = { document.value = it },
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
                onClick = {
                    addressSelected.value = item
                    openCreateAddressBottomSheet()
                },
                onDeleteAddress = {
                    addressSelected.value = item
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
                onClick = { openCreateAddressBottomSheet() },
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

    if (createAddressBottomSheet.isVisible) {
        AddAddress(
            addressSelected = addressSelected.value,
            modifier = Modifier.imePadding(),
            sheetState = createAddressBottomSheet,
            createAddress = {
                sendIntent(CreateOrUpdateUserAction.AddAddressInUser(it))
                closeCreateAddressBottomSheet()
            },
            updateAddress = {
                sendIntent(CreateOrUpdateUserAction.UpdateAddressInUser(it))
                closeCreateAddressBottomSheet()
            }
        )
    }

    if (deleteAddressDialog.value) {
        ConfirmDeleteAddress(
            onConfirmDeleteAddress = {
                addressSelected.value?.let {
                    sendIntent(CreateOrUpdateUserAction.RemoveAddressOfUser(it))
                }
                addressSelected.value = null
                deleteAddressDialog.hide()
            },
            onCancel = {
                addressSelected.value = null
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddAddress(
    addressSelected: Address? = null,
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    createAddress: (Address) -> Unit,
    updateAddress: (Address) -> Unit,
) {
    // Region State
    val addressTypeSelected = remember { mutableStateOf(AddressTypeEnum.RESIDENTIAL) }
    val address = remember { mutableStateOf(addressSelected?.street ?: EMPTY_STRING) }
    val number = remember { mutableStateOf<String?>(addressSelected?.number ?: EMPTY_STRING) }
    val complement =
        remember { mutableStateOf<String?>(addressSelected?.complement ?: EMPTY_STRING) }
    val neighborhood = remember { mutableStateOf(addressSelected?.neighborhood ?: EMPTY_STRING) }
    val cep = remember { mutableStateOf(addressSelected?.zipCode ?: EMPTY_STRING) }
    val city = remember { mutableStateOf(addressSelected?.city ?: EMPTY_STRING) }
    val state = remember { mutableStateOf(addressSelected?.state ?: EMPTY_STRING) }
    // Region Visual
    val visualTransformationCep: VisualTransformation = remember { cepVisualTransformation() }
    val buttonShouldBeEnabled = (
        address.value.isNotEmpty() &&
            neighborhood.value.isNotEmpty() &&
            cep.value.length == 8 &&
            city.value.isNotEmpty() &&
            state.value.isNotEmpty()
    )

    AppBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = {},
    ) {
        Column(
            modifier =
                modifier
                    .verticalScroll(rememberScrollState())
                    .imePadding(),
        ) {
            Text(
                text = "Adicionar endereço",
                style = TextType.h1,
            )

            Text(
                text = "Tipo de endereço",
                style = TextType.subtitle1,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = spacingNormal),
            )

            AppSelectAddressType(
                modifier = Modifier.padding(top = spacingTiny),
                addressTypeSelected = addressTypeSelected.value,
            ) { addressTypeSelected.value = it }

            AppTextField(
                modifier = Modifier.padding(top = spacingSmall),
                value = address.value,
                textChanged = { address.value = it },
                maxLines = 1,
                keyboardType = KeyboardType.Text,
                showTitle = true,
                placeholder = "Rua*",
            )

            AppTextField(
                modifier = Modifier.padding(top = spacingSmall),
                value = number.value ?: EMPTY_STRING,
                textChanged = { number.value = it },
                maxLines = 1,
                showTitle = true,
                keyboardType = KeyboardType.Number,
                placeholder = "Número",
            )

            AppTextField(
                modifier = Modifier.padding(top = spacingSmall),
                value = complement.value ?: EMPTY_STRING,
                textChanged = { complement.value = it },
                maxLines = 1,
                showTitle = true,
                placeholder = "Complemento",
            )

            AppTextField(
                modifier = Modifier.padding(top = spacingSmall),
                value = neighborhood.value,
                keyboardType = KeyboardType.Text,
                textChanged = { neighborhood.value = it },
                maxLines = 1,
                showTitle = true,
                placeholder = "Bairro*",
            )

            AppTextField(
                modifier = Modifier.padding(top = spacingSmall),
                value = cep.value,
                keyboardType = KeyboardType.Number,
                visualTransformation = visualTransformationCep,
                textChanged = { cep.value = it },
                maxLines = 1,
                showTitle = true,
                placeholder = "Cep*",
            )

            AppTextField(
                modifier = Modifier.padding(top = spacingSmall),
                value = city.value,
                keyboardType = KeyboardType.Text,
                textChanged = { city.value = it },
                maxLines = 1,
                showTitle = true,
                placeholder = "Cidade*",
            )

            AppTextField(
                modifier = Modifier.padding(top = spacingSmall),
                value = state.value,
                keyboardType = KeyboardType.Text,
                textChanged = { state.value = it },
                maxLines = 1,
                showTitle = true,
                placeholder = "Estado*",
            )

            AppButton(
                modifier =
                    Modifier
                        .padding(top = spacingNormal)
                        .fillMaxWidth(),
                enabled = buttonShouldBeEnabled,
                text =
                    if (addressSelected == null) {
                        "Adicionar"
                    } else {
                        "Atualizar"
                    },
                onClick = {
                    val newAddress =
                        Address(
                            id = addressSelected?.id ?: BsonObjectId().toHexString(),
                            type = addressTypeSelected.value,
                            street = address.value,
                            number = number.value,
                            complement = complement.value,
                            neighborhood = neighborhood.value,
                            zipCode = cep.value,
                            city = city.value,
                            state = state.value,
                        )

                    if (addressSelected == null) {
                        createAddress(newAddress)
                    } else {
                        updateAddress(newAddress)
                    }
                },
            )
        }
    }
}
