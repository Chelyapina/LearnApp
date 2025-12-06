package com.example.security.storage

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.example.security.SecurityConstants
import com.example.security.SecurityManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SecureStorageImpl @Inject constructor(
    private val securityManager : SecurityManager
) : SecureStorage {

    private val prefs : SharedPreferences by lazy {
        securityManager.createEncryptedSharedPreferences(SecurityConstants.AUTH_SECURE_PREFS_NAME)
    }

    override suspend fun saveToken(token : String) = withContext(Dispatchers.IO) {
        try {
            prefs.edit { putString(SecurityConstants.KEY_AUTH_TOKEN, token) }
        } catch (e : Exception) {
            Log.e(ErrorMessages.TAG, ErrorMessages.SAVE_TOKEN_ERROR, e)
            throw e
        }
    }

    override suspend fun getToken() : String? = withContext(Dispatchers.IO) {
        try {
            prefs.getString(SecurityConstants.KEY_AUTH_TOKEN, null)
        } catch (e : Exception) {
            Log.e(ErrorMessages.TAG, ErrorMessages.GET_TOKEN_ERROR, e)
            null
        }
    }

    override suspend fun clearToken() = withContext(Dispatchers.IO) {
        try {
            prefs.edit { remove(SecurityConstants.KEY_AUTH_TOKEN) }
        } catch (e : Exception) {
            Log.e(ErrorMessages.TAG, ErrorMessages.CLEAR_TOKEN_ERROR, e)
            throw e
        }
    }

    companion object {
        private object ErrorMessages {
            const val TAG = "SecureStorage"
            const val SAVE_TOKEN_ERROR = "Error saving token"
            const val GET_TOKEN_ERROR = "Error getting token"
            const val CLEAR_TOKEN_ERROR = "Error clearing token"
        }
    }
}