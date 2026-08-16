package com.code4galaxy.e_commerceapp.utils

import android.content.Context

class SessionManager(
    context: Context
) {

    private val sharedPreferences =
        context.getSharedPreferences(
            "user_session",
            Context.MODE_PRIVATE
        )

    fun saveUser(
        userId: String,
        fullName: String,
        email: String,
        mobile: String
    ) {

        sharedPreferences.edit()
            .putString("userId", userId)
            .putString("fullName", fullName)
            .putString("email", email)
            .putString("mobile", mobile)
            .putBoolean("isLoggedIn", true)
            .apply()
    }

    fun getUserId(): String {
        return sharedPreferences.getString(
            "userId",
            ""
        ) ?: ""
    }

    fun getFullName(): String {
        return sharedPreferences.getString(
            "fullName",
            ""
        ) ?: ""
    }

    fun getEmail(): String {
        return sharedPreferences.getString(
            "email",
            ""
        ) ?: ""
    }

    fun getMobile(): String {
        return sharedPreferences.getString(
            "mobile",
            ""
        ) ?: ""
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(
            "isLoggedIn",
            false
        )
    }

    fun logout() {
        sharedPreferences.edit()
            .clear()
            .apply()
    }
}