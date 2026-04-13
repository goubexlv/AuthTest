package cm.daccvo.auth.utils

object Validators {
    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        return email.matches(emailRegex)
    }

    fun isValidPhone(phone: String): Boolean {
        val cleanedPhone = phone.replace(" ", "")
        val cameroonPhoneRegex =
            "^(\\+237|237)?[26][0-9]{8}$".toRegex()

        return cleanedPhone.matches(cameroonPhoneRegex)
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 8 &&
                password.any { it.isUpperCase() } &&
                password.any { it.isLowerCase() } &&
                password.any { it.isDigit() }
    }

    fun getPasswordStrength(password: String): Int {
        var strength = 0
        if (password.length >= 8) strength++
        if (password.any { it.isUpperCase() }) strength++
        if (password.any { it.isLowerCase() }) strength++
        if (password.any { it.isDigit() }) strength++
        if (password.any { !it.isLetterOrDigit() }) strength++
        return strength
    }

    fun getPasswordStrengthLabel(strength: Int): String {
        return when (strength) {
            0, 1 -> "Weak"
            2, 3 -> "Medium"
            4, 5 -> "Strong"
            else -> "Weak"
        }
    }
}