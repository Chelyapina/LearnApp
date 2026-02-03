package com.example.authorization.presentation.validation

object AuthValidation {
    sealed class ValidationResult {
        data object Valid : ValidationResult()
        data class Invalid(val errorMessage: String) : ValidationResult()
    }

    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult.Invalid(ErrorMessages.EMPTY_EMAIL)
            !email.matches(Constraints.ENGLISH_LETTERS_PATTERN) ->
                ValidationResult.Invalid(ErrorMessages.NON_ENGLISH_PASSWORD)
            else -> ValidationResult.Valid
        }
    }

    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult.Invalid(ErrorMessages.EMPTY_PASSWORD)
            password.length < Constraints.MIN_PASSWORD_LENGTH ->
                ValidationResult.Invalid(ErrorMessages.passwordTooShort())
            password.length > Constraints.MAX_PASSWORD_LENGTH ->
                ValidationResult.Invalid(ErrorMessages.passwordTooLong())
            !password.matches(Constraints.ENGLISH_LETTERS_PATTERN) ->
                ValidationResult.Invalid(ErrorMessages.NON_ENGLISH_PASSWORD)
            else -> ValidationResult.Valid
        }
    }

    object Constraints {
        const val MIN_PASSWORD_LENGTH = 6
        const val MAX_PASSWORD_LENGTH = 12
        val ENGLISH_LETTERS_PATTERN = Regex("^[a-zA-Z]*\$")
    }

    object ErrorMessages {
        const val EMPTY_EMAIL = "Введите email"
        const val EMPTY_PASSWORD = "Введите пароль"

        fun passwordTooShort() =
                "Пароль должен содержать минимум ${Constraints.MIN_PASSWORD_LENGTH} символов"

        fun passwordTooLong() =
                "Пароль не должен превышать ${Constraints.MAX_PASSWORD_LENGTH} символов"

        const val NON_ENGLISH_PASSWORD = "Используйте только английские буквы"
    }
}