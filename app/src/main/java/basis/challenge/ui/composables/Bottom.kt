package basis.challenge.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import basis.challenge.R
import basis.challenge.utils.constants.EMPTY_STRING
import basis.challenge.utils.theme.RedPrimary
import basis.challenge.utils.theme.spacingNormal

@Composable
fun Bottom(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier
            .background(RedPrimary)
            .fillMaxWidth()
            .padding(vertical = spacingNormal),
    ) {
        val image = createRef()

        Image(
            modifier =
                Modifier.constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            painter = painterResource(id = R.drawable.ic_basis),
            contentDescription = EMPTY_STRING,
        )
    }
}
