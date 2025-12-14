package com.example.designsystem.state

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.designsystem.R

sealed class AlertConfig(
    @StringRes val titleRes : Int,
    @StringRes val messageRes : Int,
    @StringRes val confirmTextRes : Int
) {
    object NetworkError : AlertConfig(
        titleRes = R.string.network_error_title,
        messageRes = R.string.network_error_message,
        confirmTextRes = R.string.network_error_button
    )

    data class ServerError(val code : Int) : AlertConfig(
        titleRes = R.string.server_error_title,
        messageRes = R.string.server_error_message_template,
        confirmTextRes = R.string.server_error_button
    )

    object TimeoutError : AlertConfig(
        titleRes = R.string.timeout_error_title,
        messageRes = R.string.timeout_error_message,
        confirmTextRes = R.string.timeout_error_button
    )

    object UnauthorizedError : AlertConfig(
        titleRes = R.string.unauthorized_error_title,
        messageRes = R.string.unauthorized_error_message,
        confirmTextRes = R.string.unauthorized_error_button
    )

    object GenericError : AlertConfig(
        titleRes = R.string.generic_error_title,
        messageRes = R.string.generic_error_message,
        confirmTextRes = R.string.generic_error_button
    )
}

@Composable
fun AlertConfig.getTitle() : String {
    return when (this) {
        is AlertConfig.ServerError -> stringResource(this.titleRes)
        else -> stringResource(titleRes)
    }
}

@Composable
fun AlertConfig.getMessage() : String {
    return when (this) {
        is AlertConfig.ServerError -> stringResource(messageRes, code)
        else -> stringResource(messageRes)
    }
}

@Composable
fun AlertConfig.getConfirmText() : String = stringResource(confirmTextRes)