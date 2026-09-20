package com.example.data.auth

import android.content.Context
import android.util.Base64
import android.util.Log
import com.example.data.model.UserProfileEntity
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AuthSession(
    val userId: String,
    val userEmail: String,
    val role: String,
    val isAuthenticated: Boolean,
    val authTokenEncrypted: String
)

/**
 * Robust authentication and encryption manager.
 * Implements AES-256 session token encryption and role-based access control (RBAC).
 */
class AuthManager(private val context: Context) {

    private val _sessionState = MutableStateFlow(
        AuthSession(
            userId = "VR-9428-IN",
            userEmail = "chieftain.bharat@villagerush.in",
            role = "Chieftain (Admin)",
            isAuthenticated = true,
            authTokenEncrypted = encryptToken("SESSION_VR_BHARAT_ROOT_2026")
        )
    )
    val sessionState: StateFlow<AuthSession> = _sessionState.asStateFlow()

    companion object {
        private const val TAG = "AuthManager"
        private const val SECRET_SEED = "VillageRushBharatAES256KeySeed2026!"
        private val INIT_VECTOR = byteArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)

        fun encryptToken(plainText: String): String {
            return try {
                val sha = MessageDigest.getInstance("SHA-256")
                val key = sha.digest(SECRET_SEED.toByteArray(Charsets.UTF_8))
                val secretKeySpec = SecretKeySpec(key, "AES")
                val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, IvParameterSpec(INIT_VECTOR))
                val encrypted = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
                Base64.encodeToString(encrypted, Base64.NO_WRAP)
            } catch (e: Exception) {
                Log.e(TAG, "Encryption error", e)
                "ENC_${plainText.hashCode()}"
            }
        }

        fun decryptToken(encryptedText: String): String {
            return try {
                val sha = MessageDigest.getInstance("SHA-256")
                val key = sha.digest(SECRET_SEED.toByteArray(Charsets.UTF_8))
                val secretKeySpec = SecretKeySpec(key, "AES")
                val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, IvParameterSpec(INIT_VECTOR))
                val decoded = Base64.decode(encryptedText, Base64.NO_WRAP)
                String(cipher.doFinal(decoded), Charsets.UTF_8)
            } catch (e: Exception) {
                Log.e(TAG, "Decryption error", e)
                "INVALID_OR_CORRUPT_TOKEN"
            }
        }
    }

    fun switchRole(newRole: String) {
        _sessionState.value = _sessionState.value.copy(
            role = newRole,
            authTokenEncrypted = encryptToken("TOKEN_${newRole}_${System.currentTimeMillis()}")
        )
    }

    /**
     * RBAC Permission check
     */
    fun hasPermission(action: String): Boolean {
        val currentRole = _sessionState.value.role
        return when (action) {
            "MANAGE_BUILDINGS" -> currentRole.contains("Admin") || currentRole.contains("Moderator")
            "SPEND_TREASURY" -> currentRole.contains("Admin")
            "VIEW_DIAGNOSTICS" -> currentRole.contains("Admin") || currentRole.contains("Moderator")
            "RESET_DATABASE" -> currentRole.contains("Admin")
            else -> true // General game actions allowed for Runner/Player
        }
    }
}
