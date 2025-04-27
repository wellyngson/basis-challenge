package basis.challenge.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import basis.challenge.R
import basis.challenge.domain.model.Address
import basis.challenge.utils.constants.EMPTY_STRING
import basis.challenge.utils.enums.AddressTypeEnum
import basis.challenge.utils.extensions.transparentButtonBackground
import basis.challenge.utils.theme.TextType
import basis.challenge.utils.theme.spacingLarge
import basis.challenge.utils.theme.spacingNormal
import basis.challenge.utils.theme.spacingTiny
import basis.challenge.utils.theme.spacingXLarge
import basis.challenge.utils.theme.spacingXXLarge
import org.mongodb.kbson.ObjectId

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
fun CreateAddressDialog(
    onClose: () -> Unit = {},
    createAddress: (Address) -> Unit,
) {
    val addressTypeSelected = remember { mutableStateOf(AddressTypeEnum.RESIDENTIAL) }
    val address = remember { mutableStateOf(EMPTY_STRING) }
    val number = remember { mutableStateOf<String?>(EMPTY_STRING) }
    val complement = remember { mutableStateOf<String?>(EMPTY_STRING) }
    val neighborhood = remember { mutableStateOf(EMPTY_STRING) }
    val cep = remember { mutableStateOf(EMPTY_STRING) }
    val city = remember { mutableStateOf(EMPTY_STRING) }
    val state = remember { mutableStateOf(EMPTY_STRING) }
    val buttonShouldBeEnabled = (
        address.value.isNotEmpty() &&
            neighborhood.value.isNotEmpty() &&
            cep.value.isNotEmpty() &&
            city.value.isNotEmpty() &&
            state.value.isNotEmpty()
    )

    AppDialog {
        Column(
            modifier =
                Modifier
                    .verticalScroll(rememberScrollState())
                    .imePadding(),
        ) {
            ConstraintLayout {
                val (
                    title,
                    close,
                    description,
                    addressType,
                    addressConstraint,
                    numberConstraint,
                    complementConstraint,
                    neighborhoodConstraint,
                    cepConstraint,
                    cityConstraint,
                    stateConstraint,
                    create,
                ) = createRefs()

                Text(
                    text = "Adicionar endereço",
                    style = TextType.h1,
                    modifier =
                        Modifier.constrainAs(title) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        },
                )

                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    tint = Color.Black,
                    contentDescription = null,
                    modifier =
                        Modifier
                            .constrainAs(close) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                            }.clip(CircleShape)
                            .clickable { onClose() },
                )

                Text(
                    text = "Tipo de endereço",
                    style = TextType.subtitle1,
                    textAlign = TextAlign.Start,
                    modifier =
                        Modifier.constrainAs(description) {
                            top.linkTo(title.bottom, spacingXXLarge)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        },
                )

                AppSelectAddressType(
                    modifier =
                        Modifier
                            .constrainAs(addressType) {
                                top.linkTo(description.bottom, spacingNormal)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                    addressTypeSelected = addressTypeSelected.value,
                ) { addressTypeSelected.value = it }

                AppTextField(
                    modifier =
                        Modifier.constrainAs(addressConstraint) {
                            top.linkTo(addressType.bottom, spacingNormal)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    value = address.value,
                    textChanged = { address.value = it },
                    maxLines = 1,
                    keyboardType = KeyboardType.Text,
                    showTitle = true,
                    placeholder = "Rua*",
                )

                AppTextField(
                    modifier =
                        Modifier.constrainAs(numberConstraint) {
                            top.linkTo(addressConstraint.bottom, spacingNormal)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    value = number.value ?: EMPTY_STRING,
                    textChanged = { number.value = it },
                    maxLines = 1,
                    showTitle = true,
                    keyboardType = KeyboardType.Number,
                    placeholder = "Número",
                )

                AppTextField(
                    modifier =
                        Modifier.constrainAs(complementConstraint) {
                            top.linkTo(numberConstraint.bottom, spacingNormal)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    value = complement.value ?: EMPTY_STRING,
                    textChanged = { complement.value = it },
                    maxLines = 1,
                    showTitle = true,
                    placeholder = "Complemento",
                )

                AppTextField(
                    modifier =
                        Modifier.constrainAs(neighborhoodConstraint) {
                            top.linkTo(complementConstraint.bottom, spacingNormal)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    value = neighborhood.value,
                    keyboardType = KeyboardType.Text,
                    textChanged = { neighborhood.value = it },
                    maxLines = 1,
                    showTitle = true,
                    placeholder = "Bairro*",
                )

                AppTextField(
                    modifier =
                        Modifier.constrainAs(cepConstraint) {
                            top.linkTo(neighborhoodConstraint.bottom, spacingNormal)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    value = cep.value,
                    keyboardType = KeyboardType.Number,
                    textChanged = { cep.value = it },
                    maxLines = 1,
                    showTitle = true,
                    placeholder = "Cep*",
                )

                AppTextField(
                    modifier =
                        Modifier.constrainAs(cityConstraint) {
                            top.linkTo(cepConstraint.bottom, spacingNormal)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    value = city.value,
                    keyboardType = KeyboardType.Text,
                    textChanged = { city.value = it },
                    maxLines = 1,
                    showTitle = true,
                    placeholder = "Cidade*",
                )

                AppTextField(
                    modifier =
                        Modifier.constrainAs(stateConstraint) {
                            top.linkTo(cityConstraint.bottom, spacingNormal)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
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
                            .constrainAs(create) {
                                top.linkTo(stateConstraint.bottom, spacingNormal)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }.fillMaxWidth(),
                    enabled = buttonShouldBeEnabled,
                    text = "Adicionar",
                    onClick = {
                        val addressToCreate =
                            Address(
                                id = ObjectId().toHexString(),
                                type = addressTypeSelected.value,
                                street = address.value,
                                number = number.value,
                                complement = complement.value,
                                neighborhood = neighborhood.value,
                                zipCode = cep.value,
                                city = city.value,
                                state = state.value,
                            )

                        createAddress(addressToCreate)
                    },
                )
            }
        }
    }
}

@Composable
fun ConfirmDeleteAddress(
    onConfirmDeleteAddress: () -> Unit,
    onCancel: () -> Unit,
) {
    AppDialog {
        ConstraintLayout(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            val (title, description, delete, cancel) = createRefs()

            Text(
                text = "Excluir endereço",
                style = TextType.h1,
                textAlign = TextAlign.Center,
                modifier =
                    Modifier.constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.matchParent
                    },
            )

            Text(
                text = "Após realizar essa ação, ela não poderá ser desfeita. Deseja continuar?",
                style = TextType.subtitle1,
                textAlign = TextAlign.Center,
                modifier =
                    Modifier.constrainAs(description) {
                        top.linkTo(title.bottom, spacingNormal)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.matchParent
                    },
            )

            AppButton(
                modifier =
                    Modifier
                        .constrainAs(cancel) {
                            top.linkTo(description.bottom, spacingXLarge)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }.fillMaxWidth(),
                text = "Cancelar",
                backgroundStyle = transparentButtonBackground(),
                onClick = onCancel,
            )

            AppButton(
                modifier =
                    Modifier
                        .constrainAs(delete) {
                            top.linkTo(cancel.bottom, spacingTiny)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }.fillMaxWidth(),
                text = "Excluir",
                onClick = onConfirmDeleteAddress,
            )
        }
    }
}

@Composable
fun ConfirmDeleteUser(
    onConfirmDeleteUser: () -> Unit,
    onCancel: () -> Unit,
) {
    AppDialog {
        ConstraintLayout(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            val (title, description, delete, cancel) = createRefs()

            Text(
                text = "Excluir usuário",
                style = TextType.h1,
                textAlign = TextAlign.Center,
                modifier =
                    Modifier.constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.matchParent
                    },
            )

            Text(
                text = "Após realizar essa ação, ela não poderá ser desfeita. Deseja continuar?",
                style = TextType.subtitle1,
                textAlign = TextAlign.Center,
                modifier =
                    Modifier.constrainAs(description) {
                        top.linkTo(title.bottom, spacingNormal)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.matchParent
                    },
            )

            AppButton(
                modifier =
                    Modifier
                        .constrainAs(cancel) {
                            top.linkTo(description.bottom, spacingXLarge)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }.fillMaxWidth(),
                text = "Cancelar",
                backgroundStyle = transparentButtonBackground(),
                onClick = onCancel,
            )

            AppButton(
                modifier =
                    Modifier
                        .constrainAs(delete) {
                            top.linkTo(cancel.bottom, spacingTiny)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }.fillMaxWidth(),
                text = "Excluir",
                onClick = onConfirmDeleteUser,
            )
        }
    }
}

@Composable
fun UserDeletedWithSuccessDialog(onClick: () -> Unit) {
    AppDialog {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Usuário deletado com sucesso!",
                style = TextType.h1,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            AppButton(
                text = "Fechar",
                onClick = onClick,
                modifier = Modifier.padding(top = spacingXLarge).fillMaxWidth(),
            )
        }
    }
}
