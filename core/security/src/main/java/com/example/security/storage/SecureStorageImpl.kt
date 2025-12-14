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

    override suspend fun getToken() : String? = withContext(Dispatchers.IO) {
        try {
            prefs.getString(SecurityConstants.KEY_AUTH_TOKEN, null)
        } catch (e : Exception) {
            Log.e(ErrorMessages.TAG, ErrorMessages.GET_TOKEN_ERROR, e)
            null
        }
    }

    override suspend fun getUserName() : String? = withContext(Dispatchers.IO) {
        try {
            prefs.getString(SecurityConstants.KEY_USER_NAME, null)
        } catch (e : Exception) {
            Log.e(ErrorMessages.TAG, ErrorMessages.GET_USER_NAME_ERROR, e)
            null
        }
    }

    override suspend fun saveUserData(userName : String, token : String) =
            withContext(Dispatchers.IO) {
                try {
                    prefs.edit {
                        putString(SecurityConstants.KEY_USER_NAME, userName)
                        putString(SecurityConstants.KEY_AUTH_TOKEN, token)
                    }
                } catch (e : Exception) {
                    Log.e(ErrorMessages.TAG, ErrorMessages.SAVE_USER_DATA_ERROR, e)
                    throw e
                }
            }

    override suspend fun clearUserData() = withContext(Dispatchers.IO) {
        try {
            prefs.edit {
                remove(SecurityConstants.KEY_USER_NAME)
                remove(SecurityConstants.KEY_AUTH_TOKEN)
            }
        } catch (e : Exception) {
            Log.e(ErrorMessages.TAG, ErrorMessages.CLEAR_USER_DATA_ERROR, e)
            throw e
        }
    }

    override suspend fun hasUserData() : Boolean = withContext(Dispatchers.IO) {
        try {
            val userName = prefs.getString(SecurityConstants.KEY_USER_NAME, null)
            val token = prefs.getString(SecurityConstants.KEY_AUTH_TOKEN, null)
            !userName.isNullOrEmpty() && !token.isNullOrEmpty()
        } catch (e : Exception) {
            Log.e(ErrorMessages.TAG, ErrorMessages.CHECK_USER_DATA_ERROR, e)
            false
        }
    }

    companion object {
        private object ErrorMessages {
            const val TAG = "SecureStorage"
            const val GET_TOKEN_ERROR = "Error getting token"
            const val GET_USER_NAME_ERROR = "Error getting user name"
            const val SAVE_USER_DATA_ERROR = "Error saving user data"
            const val CLEAR_USER_DATA_ERROR = "Error clearing user data"
            const val CHECK_USER_DATA_ERROR = "Error checking user data"
        }
    }
}