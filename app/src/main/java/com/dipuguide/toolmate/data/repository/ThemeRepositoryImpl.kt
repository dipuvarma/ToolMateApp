package com.dipuguide.toolmate.data.repository

import androidx.appcompat.app.AppCompatDelegate
import com.dipuguide.toolmate.domain.repository.ThemeRepository
import javax.inject.Inject

class ThemeRepositoryImpl
@Inject
constructor() : ThemeRepository {
    override fun applyTheme(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )
        } else {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }
}
