package basis.challenge.data.model

import basis.challenge.utils.constants.EMPTY_STRING
import basis.challenge.utils.enums.AddressTypeEnum
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class AddressEntity : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var type: String = AddressTypeEnum.RESIDENTIAL.name
    var street: String = ""
    var number: String? = null
    var complement: String? = null
    var neighborhood: String = EMPTY_STRING
    var zipCode: String = EMPTY_STRING
    var city: String = EMPTY_STRING
    var state: String = EMPTY_STRING
}
