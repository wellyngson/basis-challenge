package basis.challenge.domain.model

import android.os.Parcelable
import basis.challenge.data.model.UserEntity
import basis.challenge.domain.model.Address.Companion.toEntity
import basis.challenge.domain.model.Address.Companion.toUi
import basis.challenge.utils.constants.EMPTY_STRING
import basis.challenge.utils.enums.PersonTypeEnum
import basis.challenge.utils.extensions.toObjectId
import io.realm.kotlin.ext.toRealmList
import kotlinx.parcelize.Parcelize
import org.mongodb.kbson.ObjectId

@Parcelize
data class User(
    val id: String,
    val personType: PersonTypeEnum,
    val name: String,
    val document: String,
    val phone: String,
    val email: String? = null,
    val addresses: List<Address> = emptyList(),
) : Parcelable {
    companion object {
        fun UserEntity.toUi(): User =
            User(
                id = this._id.toHexString(),
                personType =
                    PersonTypeEnum.fromString(this.personType)
                        ?: PersonTypeEnum.INDIVIDUAL,
                name = this.name,
                document = this.document,
                phone = this.phone,
                email = this.email,
                addresses = this.address.map { it.toUi() },
            )

        fun User.toEntity(): UserEntity =
            UserEntity().also {
                it._id = this.id.toObjectId()
                it.personType = this.personType.name
                it.name = this.name
                it.document = this.document
                it.phone = this.phone
                it.email = this.email
                it.address = this.addresses.map { address -> address.toEntity() }.toRealmList()
            }

        fun initializeEmptyUser(): User =
            User(
                id = ObjectId().toHexString(),
                personType = PersonTypeEnum.INDIVIDUAL,
                name = EMPTY_STRING,
                document = EMPTY_STRING,
                phone = EMPTY_STRING,
                email = null,
                addresses = emptyList(),
            )
    }
}
