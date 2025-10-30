package com.example.parcialtp3.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * TokenManager maneja el almacenamiento seguro del token de autenticación
 * usando EncryptedSharedPreferences para cifrar los datos
 */
@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private const val PREFS_NAME = "secure_prefs"
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_USER_ID = "user_id"
    }

    /**
     * Guarda el token de autenticación de forma segura
     */
    fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_AUTH_TOKEN, token).apply()
    }

    /**
     * Obtiene el token de autenticación guardado
     * @return El token guardado o null si no existe
     */
    fun getToken(): String? {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null)
    }

    /**
     * Guarda el ID del usuario
     */
    fun saveUserId(userId: Int) {
        sharedPreferences.edit().putInt(KEY_USER_ID, userId).apply()
    }

    /**
     * Obtiene el ID del usuario guardado
     * @return El ID del usuario o -1 si no existe
     */
    fun getUserId(): Int {
        return sharedPreferences.getInt(KEY_USER_ID, -1)
    }

    /**
     * Verifica si hay una sesión activa (token guardado)
     */
    fun isLoggedIn(): Boolean {
        return !getToken().isNullOrEmpty()
    }

    /**
     * Limpia todos los datos de autenticación (logout)
     */
    fun clearToken() {
        sharedPreferences.edit().clear().apply()
    }
}
