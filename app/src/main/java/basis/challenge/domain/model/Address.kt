package basis.challenge.domain.model

import android.os.Parcelable
import basis.challenge.data.model.AddressEntity
import basis.challenge.utils.enums.AddressTypeEnum
import basis.challenge.utils.extensions.toObjectId
import kotlinx.parcelize.Parcelize
import org.mongodb.kbson.ObjectId

@Parcelize
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
) : Parcelable {
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

        fun Address.toEntity(): AddressEntity =
            AddressEntity().also {
                it._id = this.id.toObjectId()
                it.type = this.type.name
                it.street = this.street
                it.number = this.number
                it.complement = this.complement
                it.neighborhood = this.neighborhood
                it.zipCode = this.zipCode
                it.city = this.city
                it.state = this.state
            }

        fun Address.buildAddressName(): String {
            val addressParts = mutableListOf<String>()

            this.street.takeIf { it.isNotEmpty() }?.let { addressParts.add(it) }
            this.number?.takeIf { it.isNotEmpty() }?.let { addressParts.add(it) }
            this.complement?.takeIf { it.isNotEmpty() }?.let { addressParts.add(it) }
            this.neighborhood.takeIf { it.isNotEmpty() }?.let { addressParts.add(it) }
            this.city.takeIf { it.isNotEmpty() }?.let { addressParts.add(it) }
            this.state.takeIf { it.isNotEmpty() }?.let { addressParts.add(it) }
            return addressParts.joinToString(" - ")
        }

        fun initializeEmptyAddress(): Address =
            Address(
                id = ObjectId().toHexString(),
                type = AddressTypeEnum.RESIDENTIAL,
                street = "",
                number = null,
                complement = null,
                neighborhood = "",
                zipCode = "",
                city = "",
                state = "",
            )
    }
}
