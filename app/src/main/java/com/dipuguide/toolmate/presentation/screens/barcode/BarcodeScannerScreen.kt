package com.dipuguide.toolmate.presentation.screens.barcode

import android.Manifest
import android.content.Context
import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.CameraProvider
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.AspectRatioStrategy.FALLBACK_RULE_NONE
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.dipuguide.toolmate.presentation.common.state.UiState
import com.dipuguide.toolmate.utils.PermissionHandler
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.launch

@Composable
fun BarcodeScannerScreen(
    viewModel: BarcodeScannerViewModel,
) {
    var permissionGranted by remember { mutableStateOf(false) }
    val barcodeState = viewModel.barcodeResult.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState) {
        when (uiState) {
            is UiState.Error -> {
                val errorMessage = (uiState as UiState.Error).message
                Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                Log.e("BarcodeScreen", "Error: $errorMessage")
                viewModel.resetUiState()
            }

            UiState.Idle -> {

            }

            UiState.Loading -> {

            }

            is UiState.Success -> {

            }
        }
    }

    PermissionHandler(
        onPermissionGranted = {
            permissionGranted = true
        },
        onPermissionDenied = {
            permissionGranted = false
        }
    )
    if (permissionGranted) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            //For Camera Preview
            CameraPreview(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                analyzeLive = { imageProxy ->
                    viewModel.scanBarcodeLive(imageProxy = imageProxy)
                }
            )
            Spacer(modifier = Modifier.height(42.dp))
            //For Barcode Result
            if (barcodeState.value.isNotEmpty()) {
                LazyColumn {
                    items(barcodeState.value) { barcode ->
                        Text(
                            text = "Result: ${barcode.barcode.rawValue}",
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }
                }
            }
        }

    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Please grant camera permission in settings.")
        }
    }

}




