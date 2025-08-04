package com.dipuguide.toolmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dipuguide.toolmate.presentation.navigation.ToolMateNavGraph
import com.dipuguide.toolmate.presentation.screens.barcode.BarcodeScannerViewModel
import com.dipuguide.toolmate.presentation.screens.barcode.CameraPreview
import com.dipuguide.toolmate.presentation.ui.theme.ToolMateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel: BarcodeScannerViewModel by viewModels()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToolMateTheme() {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ToolMateNavGraph(viewModel)
                }
            }
        }
    }
}
