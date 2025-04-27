package basis.challenge.utils.extensions

import android.util.Patterns
import org.mongodb.kbson.ObjectId

fun String.toObjectId(): ObjectId = ObjectId(this)

fun String.onlyDigits(): String = this.filter { it.isDigit() }

fun String.isValidCpf(): Boolean {
    val cpf = this.onlyDigits()
    if (cpf.length != 11 || cpf.all { it == cpf[0] }) return false
    val digits = cpf.map { it.toString().toInt() }
    val firstCheck = (0..8).sumOf { (10 - it) * digits[it] } % 11
    val firstDigit = if (firstCheck < 2) 0 else 11 - firstCheck
    if (digits[9] != firstDigit) return false
    val secondCheck = (0..9).sumOf { (11 - it) * digits[it] } % 11
    val secondDigit = if (secondCheck < 2) 0 else 11 - secondCheck
    return digits[10] == secondDigit
}

fun String.isValidCnpj(): Boolean {
    val cnpj = this.onlyDigits()
    if (cnpj.length != 14 || cnpj.all { it == cnpj[0] }) return false
    val digits = cnpj.map { it.toString().toInt() }
    val weight1 = listOf(5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
    val sum1 = weight1.indices.sumOf { weight1[it] * digits[it] }
    val firstDigit = if (sum1 % 11 < 2) 0 else 11 - (sum1 % 11)
    if (digits[12] != firstDigit) return false
    val weight2 = listOf(6) + weight1
    val sum2 = weight2.indices.sumOf { weight2[it] * digits[it] }
    val secondDigit = if (sum2 % 11 < 2) 0 else 11 - (sum2 % 11)

    return digits[13] == secondDigit
}

fun String.isValidEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()
