package basis.challenge.domain.model

import basis.challenge.data.model.UserEntity
import basis.challenge.utils.enums.PersonTypeEnum

data class User(
    val id: String,
    val personType: PersonTypeEnum,
    val name: String,
    val document: String,
    val phone: String,
    val email: String? = null,
    val addresses: List<Address> = emptyList(),
) {
    companion object {
        fun UserEntity.toUi(): User =
            User(
                id = this._id.toHexString(),
                personType = PersonTypeEnum.fromString(this.personType) ?: PersonTypeEnum.INDIVIDUAL,
                name = this.name,
                document = this.document,
                phone = this.phone,
                email = this.email,
//                addresses = this.addressEntities.map { it.toUi() },
            )
    }
}
