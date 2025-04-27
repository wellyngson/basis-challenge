package basis.challenge.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import basis.challenge.R
import basis.challenge.domain.model.User
import basis.challenge.ui.composables.AppButton
import basis.challenge.ui.composables.AppDivider
import basis.challenge.ui.composables.AppTextField
import basis.challenge.ui.composables.Bottom
import basis.challenge.ui.composables.ConfirmDeleteUser
import basis.challenge.ui.composables.Header
import basis.challenge.ui.composables.LoadingScreen
import basis.challenge.ui.composables.UserDeletedWithSuccessDialog
import basis.challenge.utils.constants.EMPTY_STRING
import basis.challenge.utils.extensions.formatPhoneNumber
import basis.challenge.utils.extensions.hide
import basis.challenge.utils.extensions.show
import basis.challenge.utils.theme.TextType
import basis.challenge.utils.theme.spacingNormal
import basis.challenge.utils.theme.spacingSmall
import basis.challenge.utils.theme.spacingTiny
import basis.challenge.utils.theme.spacingXLarge
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    uiState: HomeState,
    result: SharedFlow<HomeResult>,
    sendIntent: (HomeAction) -> Unit,
    goToCreateOrUpdateUser: (User?) -> Unit,
) {
    val valueFiltered = remember { mutableStateOf<String?>(null) }
    val confirmDeleteDialog = remember { mutableStateOf(false) }
    val userDeletedWithSuccessDialog = remember { mutableStateOf(false) }
    val userToDeleted = remember { mutableStateOf<User?>(null) }

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (header, search, title, newUser, users, bottom) = createRefs()

        Header(
            modifier =
                Modifier.constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        )

        Text(
            text = "Usuários",
            style = TextType.h2,
            modifier =
                Modifier.constrainAs(title) {
                    top.linkTo(header.bottom, spacingXLarge)
                    start.linkTo(parent.start, spacingNormal)
                },
        )

        AppButton(
            modifier =
                Modifier.constrainAs(newUser) {
                    top.linkTo(title.top)
                    this.bottom.linkTo(title.bottom)
                    start.linkTo(title.end, 72.dp)
                    end.linkTo(parent.end, spacingNormal)

                    width = Dimension.fillToConstraints
                },
            drawable = R.drawable.ic_more,
            text = "Novo usuário",
            onClick = { goToCreateOrUpdateUser(null) },
        )

        AppTextField(
            value = valueFiltered.value ?: EMPTY_STRING,
            textChanged = { valueFiltered.value = it },
            placeholder = "Pesquise por nome, CPF/CNPJ, email e/ou telefone",
            maxLines = 1,
            modifier =
                Modifier
                    .constrainAs(search) {
                        top.linkTo(newUser.bottom, spacingXLarge)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }.padding(horizontal = spacingNormal),
        )

        Users(
            modifier =
                Modifier.constrainAs(users) {
                    top.linkTo(search.bottom, spacingNormal)
                    this.bottom.linkTo(bottom.top, spacingNormal)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            users = uiState.usersFiltered,
            onUserClicked = goToCreateOrUpdateUser,
            onDeleteUser = {
                confirmDeleteDialog.show()
                userToDeleted.value = it
            },
        )

        Bottom(
            modifier =
                Modifier.constrainAs(bottom) {
                    this.bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        )
    }

    if (confirmDeleteDialog.value) {
        ConfirmDeleteUser(
            onConfirmDeleteUser = {
                confirmDeleteDialog.hide()
                userToDeleted.value?.let {
                    sendIntent(HomeAction.DeleteUser(it))
                }
            },
            onCancel = {
                confirmDeleteDialog.hide()
                userToDeleted.value = null
            },
        )
    }

    if (userDeletedWithSuccessDialog.value) {
        UserDeletedWithSuccessDialog {
            userDeletedWithSuccessDialog.hide()
        }
    }

    if (uiState.isLoading) {
        LoadingScreen()
    }

    LaunchedEffect(valueFiltered.value) {
        sendIntent(HomeAction.FilterUsers(valueFiltered.value))
    }

    LaunchedEffect(result) {
        result.collect {
            when (it) {
                HomeResult.UserDeletedWithSuccess -> {
                    userDeletedWithSuccessDialog.show()
                    userToDeleted.value = null
                }
            }
        }
    }
}

@Composable
private fun Users(
    modifier: Modifier = Modifier,
    users: List<User>,
    onUserClicked: (User) -> Unit,
    onDeleteUser: (User) -> Unit,
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(spacingTiny)) {
        itemsIndexed(users) { index, user ->
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(spacingSmall))
                        .clickable { onUserClicked(user) }
                        .padding(vertical = spacingSmall, horizontal = spacingNormal),
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = user.name,
                        style = TextType.h2,
                        modifier = Modifier.padding(bottom = spacingTiny),
                    )

                    Text(
                        text = user.personType.code,
                        style = TextType.button3,
                        modifier = Modifier.padding(bottom = spacingTiny),
                    )

                    user.email?.let {
                        Text(
                            text = it,
                            style = TextType.subtitle1,
                            modifier = Modifier.padding(bottom = spacingTiny),
                        )
                    }

                    Text(
                        text = user.phone.formatPhoneNumber(),
                        style = TextType.subtitle1,
                    )
                }

                Column {
                    Icon(
                        painter = painterResource(R.drawable.ic_trash),
                        contentDescription = null,
                        modifier =
                            Modifier
                                .clip(CircleShape)
                                .clickable { onDeleteUser(user) },
                    )
                }
            }

            if (index != users.lastIndex) {
                AppDivider(modifier.padding(horizontal = spacingNormal))
            }
        }
    }
}
