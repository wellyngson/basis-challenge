package basis.challenge.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import basis.challenge.utils.enums.PersonTypeEnum
import basis.challenge.utils.theme.spacingSmall

@Composable
fun AppSelectPersonType(
    modifier: Modifier = Modifier,
    personTypeSelected: PersonTypeEnum,
    personTypeClicked: (PersonTypeEnum) -> Unit,
) {
    Row(
        modifier =
            modifier
                .background(Color.White, CircleShape)
                .clip(CircleShape),
    ) {
        PersonTypeEnum.entries.forEach { personType ->
            Row(
                modifier =
                    Modifier
                        .weight(1f)
                        .background(
                            if (personType == personTypeSelected) Color.Black else Color.White,
                            CircleShape,
                        ).clip(CircleShape)
                        .clickable {
                            personTypeClicked(personType)
                        }.padding(vertical = spacingSmall),
            ) {
                Text(
                    text = personType.code,
                    modifier =
                        Modifier
                            .padding(horizontal = spacingSmall)
                            .fillMaxWidth(),
                    color = if (personType == personTypeSelected) Color.White else Color.Black,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
