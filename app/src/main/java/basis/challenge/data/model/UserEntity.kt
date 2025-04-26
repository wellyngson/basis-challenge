package basis.challenge.data.model

import basis.challenge.utils.constants.EMPTY_STRING
import basis.challenge.utils.enums.PersonTypeEnum
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class UserEntity : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var personType: String = PersonTypeEnum.INDIVIDUAL.name
    var name: String = EMPTY_STRING
    var document: String = EMPTY_STRING
    var phone: String = EMPTY_STRING
    var email: String? = null
    var address: RealmList<AddressEntity> = realmListOf()
}
