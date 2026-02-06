package models.request

import kotlinx.serialization.Serializable

@Serializable
open class ConfirmRequest

@Serializable
data class ConfirmEmail(
    val email : String? = null,
    val code : String? = null
): ConfirmRequest() {
    init {
        require(code?.length == 6) { "Le code doit contenir 6 chiffres" }
        require(code.all { it.isDigit() }) { "Le code ne doit contenir que des chiffres" }
    }
}

@Serializable
data class ConfirmPhone(
    val phone : String? = null,
    val code : String? = null
): ConfirmRequest(){
    init {
        require(code?.length == 6) { "Le code doit contenir 6 chiffres" }
        require(code.all { it.isDigit() }) { "Le code ne doit contenir que des chiffres" }
    }
}
