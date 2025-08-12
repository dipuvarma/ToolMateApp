package com.dipuguide.toolmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.dipuguide.toolmate.presentation.navigation.ToolMateNavGraph
import com.dipuguide.toolmate.presentation.screens.barcode.BarcodeScannerViewModel
import com.dipuguide.toolmate.presentation.screens.main.setting.SettingViewModel
import com.dipuguide.toolmate.presentation.ui.theme.ToolMateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val settingViewModel: SettingViewModel = hiltViewModel()
            val isDarkMode by settingViewModel.isDarkModeActive.collectAsState()
            settingViewModel.initialThemeLoad(isSystemInDarkTheme())
            ToolMateTheme(
                darkTheme = isDarkMode
            ) {
                ToolMateNavGraph()
            }
        }
    }
}
