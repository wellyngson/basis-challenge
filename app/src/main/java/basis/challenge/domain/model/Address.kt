package basis.challenge.domain.model

import basis.challenge.data.model.AddressEntity
import basis.challenge.utils.enums.AddressTypeEnum

data class Address(
    val id: String,
    val type: AddressTypeEnum,
    val street: String,
    val number: String? = null,
    val complement: String? = null,
    val neighborhood: String,
    val zipCode: String,
    val city: String,
    val state: String,
) {
    companion object {
        fun AddressEntity.toUi(): Address =
            Address(
                id = this._id.toHexString(),
                type = AddressTypeEnum.fromString(this.type) ?: AddressTypeEnum.RESIDENTIAL,
                street = this.street,
                number = this.number,
                complement = this.complement,
                neighborhood = this.neighborhood,
                zipCode = this.zipCode,
                city = this.city,
                state = this.state,
            )
    }
}
