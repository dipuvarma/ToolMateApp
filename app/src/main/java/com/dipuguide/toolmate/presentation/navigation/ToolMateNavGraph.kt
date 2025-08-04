package com.dipuguide.toolmate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dipuguide.toolmate.presentation.screens.barcode.BarcodeScannerScreen
import com.dipuguide.toolmate.presentation.screens.barcode.BarcodeScannerViewModel
import com.dipuguide.toolmate.presentation.screens.home.HomeScreen
import com.google.accompanist.permissions.rememberPermissionState
import java.util.jar.Manifest

@Composable
fun ToolMateNavGraph(
    viewModel: BarcodeScannerViewModel
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = BarcodeScanner
    ) {
        composable<Home> {
            HomeScreen()
        }
        composable<BarcodeScanner> {
            BarcodeScannerScreen(
                viewModel = viewModel
            )
        }
    }

}