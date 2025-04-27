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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import basis.challenge.utils.extensions.transparentButtonBackground
import basis.challenge.utils.theme.TextType
import basis.challenge.utils.theme.spacingLarge
import basis.challenge.utils.theme.spacingNormal
import basis.challenge.utils.theme.spacingTiny
import basis.challenge.utils.theme.spacingXLarge

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
