package com.dipuguide.toolmate.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dipuguide.toolmate.presentation.screens.barcode.BarcodeScannerScreen
import com.dipuguide.toolmate.presentation.screens.barcode.BarcodeScannerViewModel
import com.dipuguide.toolmate.presentation.screens.document.DocumentScannerScreen
import com.dipuguide.toolmate.presentation.screens.imageLabeling.ImageLabelingScreen
import com.dipuguide.toolmate.presentation.screens.main.MainScreen
import com.dipuguide.toolmate.presentation.screens.obejctDetection.ObjectDetectionScreen
import com.dipuguide.toolmate.presentation.screens.speechToText.SpeechToTextScreen
import com.dipuguide.toolmate.presentation.screens.textRecognition.TextRecognitionScreen

val LocalNavController =
    staticCompositionLocalOf<NavHostController> {
        error("No NavController Provide")
    }

@Composable
fun ToolMateNavGraph() {

    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = MainRoute,
        ) {
            composable<MainRoute> {
                MainScreen()
            }
            composable<BarcodeScannerRoute> {
                BarcodeScannerScreen()
            }

            composable<DocumentScannerRoute> {
                DocumentScannerScreen()
            }

            composable<ObjectDetectionRoute> {
                ObjectDetectionScreen()
            }

            composable<ImageLabelingRoute> {
                ImageLabelingScreen()
            }

            composable<TextRecognitionRoute> {
                TextRecognitionScreen()
            }

            composable<SpeechToTextRoute> {
                SpeechToTextScreen()
            }
        }
    }
}