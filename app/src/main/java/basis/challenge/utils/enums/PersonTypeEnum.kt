package basis.challenge.utils.enums

enum class PersonTypeEnum {
    INDIVIDUAL,
    COMPANY,
    ;

    companion object {
        fun fromString(value: String): PersonTypeEnum? = entries.find { it.name.equals(value, ignoreCase = true) }
    }
}
