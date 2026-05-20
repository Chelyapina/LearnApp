package com.example.validation

object AuthValidation {
    sealed class ValidationResult {
        data object Valid : ValidationResult()
        data class Invalid(val errorMessage: String) : ValidationResult()
    }

    fun validateLogin(login: String): ValidationResult {
        return when {
            login.isBlank() -> ValidationResult.Invalid(ErrorMessages.EMPTY_LOGIN)
            !login.matches(Constraints.LOGIN_PATTERN) -> ValidationResult.Invalid(ErrorMessages.INVALID_LOGIN)
            else -> ValidationResult.Valid
        }
    }

    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult.Invalid(ErrorMessages.EMPTY_PASSWORD)
            password.length < Constraints.MIN_PASSWORD_LENGTH ->
                ValidationResult.Invalid(ErrorMessages.passwordTooShort())
            !password.matches(Constraints.PASSWORD_PATTERN) -> ValidationResult.Invalid(
                ErrorMessages.INVALID_PASSWORD
            )
            else -> ValidationResult.Valid
        }
    }

    object Constraints {
        const val MIN_PASSWORD_LENGTH = 6
        val LOGIN_PATTERN = Regex("^[a-zA-Z0-9]+\$")
        val PASSWORD_PATTERN = Regex("^[a-zA-Z0-9!@#\$%^&*()_+\\-=\\[\\]{};':\",./<>?`~]*\$")
    }

    object ErrorMessages {
        const val EMPTY_LOGIN = "Введите логин"
        const val EMPTY_PASSWORD = "Введите пароль"
        const val INVALID_LOGIN = "Логин может содержать только английские буквы и цифры"
        const val INVALID_PASSWORD =
                "Пароль может содержать только латиницу, цифры и специальные символы"

        fun passwordTooShort() =
                "Пароль должен содержать минимум ${Constraints.MIN_PASSWORD_LENGTH} символов"
    }
}