package com.dipuguide.toolmate.data.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.dipuguide.toolmate.domain.repository.SharedPreferenceRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SharedPreferenceRepositoryImpl
@Inject
constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferenceRepository {
    companion object {
        private const val TAG = "SharedPrefRepo"
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    }

    // String value save karne ka method
    override suspend fun saveString(
        key: String,
        value: String
    ) {
        withContext(ioDispatcher) {
            try {
                sharedPreferences.edit {
                    putString(key, value)
                }
                Log.d(TAG, "saveString: Key=$key, Value=$value saved.")
            } catch (e: Exception) {
                Log.e(TAG, "saveString: Failed for key=$key", e)
            }
        }
    }

    // String value read karne ka method
    override fun getString(
        key: String,
        defaultValue: String
    ): String =
        try {
            sharedPreferences.getString(key, defaultValue) ?: defaultValue
        } catch (e: Exception) {
            Log.e(TAG, "getString: Failed for key=$key", e)
            defaultValue
        }

    // Boolean value save karne ka method
    override suspend fun saveBoolean(
        key: String,
        value: Boolean
    ) {
        withContext(ioDispatcher) {
            try {
                sharedPreferences.edit {
                    putBoolean(key, value)
                }
                Log.d(TAG, "saveBoolean: Key=$key, Value=$value saved.")
            } catch (e: Exception) {
                Log.e(TAG, "saveBoolean: Failed for key=$key", e)
            }
        }
    }

    // Boolean value read karne ka method
    override fun getBoolean(
        key: String,
        defaultValue: Boolean
    ): Boolean =
        try {
            sharedPreferences.getBoolean(key, defaultValue)
        } catch (e: Exception) {
            Log.e(TAG, "getBoolean: Failed for key=$key", e)
            defaultValue
        }

    // Kisi bhi key ko remove karne ka method
    override suspend fun removeValue(key: String) {
        withContext(ioDispatcher) {
            try {
                sharedPreferences.edit {
                    remove(key)
                }
                Log.d(TAG, "removeValue: Key=$key removed.")
            } catch (e: Exception) {
                Log.e(TAG, "removeValue: Failed for key=$key", e)
            }
        }
    }

    // Saare preferences clear karne ka method
    override suspend fun clearSharedPreference() {
        withContext(ioDispatcher) {
            try {
                sharedPreferences.edit {
                    clear()
                }
                Log.d(TAG, "clearSharedPreference: All values cleared.")
            } catch (e: Exception) {
                Log.e(TAG, "clearSharedPreference: Failed", e)
            }
        }
    }
}
