package com.dipuguide.toolmate.presentation.screens.main.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dipuguide.toolmate.domain.repository.SharedPreferenceRepository
import com.dipuguide.toolmate.domain.repository.ThemeRepository
import com.dipuguide.toolmate.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SettingViewModel
@Inject
constructor(
    private val themeRepository: ThemeRepository,
    private val sharedPre: SharedPreferenceRepository
) : ViewModel() {
    private val _isDarkModeActive = MutableStateFlow<Boolean>(false)
    val isDarkModeActive: StateFlow<Boolean> = _isDarkModeActive.asStateFlow()

    fun initialThemeLoad(isSystemInDarkTheme: Boolean) {
        viewModelScope.launch {
            _isDarkModeActive.value = sharedPre.getBoolean(Constant.DARK_MODE, false)
            themeRepository.applyTheme(_isDarkModeActive.value)
        }
    }

    fun onThemeChanged(isDarkMode: Boolean) {
        viewModelScope.launch {
            _isDarkModeActive.value = isDarkMode
            sharedPre.saveBoolean(Constant.DARK_MODE, isDarkMode)
            themeRepository.applyTheme(isDarkMode)
        }
    }
}
