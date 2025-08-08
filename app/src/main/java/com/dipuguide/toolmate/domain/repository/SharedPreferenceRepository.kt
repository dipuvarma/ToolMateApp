package com.dipuguide.toolmate.domain.repository

interface SharedPreferenceRepository {
    suspend fun saveString(
        key: String,
        value: String
    )

    fun getString(
        key: String,
        defaultValue: String
    ): String

    suspend fun saveBoolean(
        key: String,
        value: Boolean
    )

    fun getBoolean(
        key: String,
        defaultValue: Boolean
    ): Boolean

    suspend fun removeValue(key: String)

    suspend fun clearSharedPreference()
}
