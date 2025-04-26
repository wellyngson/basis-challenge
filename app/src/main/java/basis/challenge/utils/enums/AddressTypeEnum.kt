package basis.challenge.utils.enums

enum class AddressTypeEnum {
    RESIDENTIAL,
    COMMERCIAL,
    ;

    companion object {
        fun fromString(value: String): AddressTypeEnum? = entries.find { it.name.equals(value, ignoreCase = true) }
    }
}
