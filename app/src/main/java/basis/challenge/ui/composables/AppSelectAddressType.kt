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
import basis.challenge.utils.enums.AddressTypeEnum
import basis.challenge.utils.theme.spacingSmall

@Composable
fun AppSelectAddressType(
    modifier: Modifier = Modifier,
    addressTypeSelected: AddressTypeEnum,
    addressTypeClicked: (AddressTypeEnum) -> Unit,
) {
    Row(
        modifier =
            modifier
                .background(Color.White, CircleShape)
                .clip(CircleShape),
    ) {
        AddressTypeEnum.entries.forEach { addressType ->
            Row(
                modifier =
                    Modifier
                        .weight(1f)
                        .background(
                            if (addressType == addressTypeSelected) Color.Black else Color.White,
                            CircleShape,
                        ).clip(CircleShape)
                        .clickable {
                            addressTypeClicked(addressType)
                        }.padding(vertical = spacingSmall),
            ) {
                Text(
                    text = addressType.code,
                    modifier =
                        Modifier
                            .padding(horizontal = spacingSmall)
                            .fillMaxWidth(),
                    color = if (addressType == addressTypeSelected) Color.White else Color.Black,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
