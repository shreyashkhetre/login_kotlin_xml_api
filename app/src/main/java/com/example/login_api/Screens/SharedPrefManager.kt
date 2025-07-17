package com.example.login_api.Helper

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveLogin(email: String) {
        prefs.edit().putBoolean("is_logged_in", true).putString("email", email).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }

    fun getEmail(): String? {
        return prefs.getString("email", null)
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}
