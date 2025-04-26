package basis.challenge.utils.enums

enum class PersonTypeEnum(
    val code: String,
) {
    INDIVIDUAL("Pessoa Física"),
    COMPANY("Pessoa Jurídica"),
    ;

    companion object {
        fun fromString(value: String): PersonTypeEnum? = entries.find { it.name.equals(value, ignoreCase = true) }
    }
}
