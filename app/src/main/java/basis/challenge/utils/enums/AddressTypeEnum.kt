package basis.challenge.utils.enums

import androidx.annotation.DrawableRes
import basis.challenge.R

enum class AddressTypeEnum(
    val code: String,
    @DrawableRes val icon: Int,
) {
    RESIDENTIAL("Residencial", R.drawable.ic_home),
    COMMERCIAL("Comercial", R.drawable.ic_company),
    ;

    companion object {
        fun fromString(value: String): AddressTypeEnum? = entries.find { it.name.equals(value, ignoreCase = true) }
    }
}
