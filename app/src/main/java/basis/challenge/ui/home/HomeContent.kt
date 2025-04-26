package basis.challenge.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import basis.challenge.R
import basis.challenge.domain.model.User
import basis.challenge.ui.composables.AppButton
import basis.challenge.ui.composables.AppTextField
import basis.challenge.ui.composables.Bottom
import basis.challenge.ui.composables.Header
import basis.challenge.ui.composables.LoadingScreen
import basis.challenge.utils.constants.EMPTY_STRING
import basis.challenge.utils.theme.TextType
import basis.challenge.utils.theme.spacingNormal
import basis.challenge.utils.theme.spacingXLarge

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    uiState: HomeState,
    addUser: () -> Unit = {},
) {
    val valueFiltered = remember { mutableStateOf<String?>(null) }

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
            onClick = addUser,
        )

        AppTextField(
            value = valueFiltered.value ?: EMPTY_STRING,
            textChanged = { valueFiltered.value = it },
            placeholder = "Pesquise por nome, CPF/CNPJ",
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
                    top.linkTo(search.bottom)
                    this.bottom.linkTo(bottom.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            users = uiState.users,
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

    if (uiState.isLoading) {
        LoadingScreen()
    }
}

@Composable
private fun Users(
    modifier: Modifier = Modifier,
    users: List<User>,
) {
    LazyColumn(modifier = modifier) {
        items(users) { user ->
            Text(user.name)
        }
    }
}
