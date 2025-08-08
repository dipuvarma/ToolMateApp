package com.dipuguide.toolmate.presentation.screens.barcode

import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dipuguide.toolmate.presentation.common.state.UiState
import com.dipuguide.toolmate.utils.CameraPermissionPlaceholder

@Composable
fun BarcodeScannerScreen(viewModel: BarcodeScannerViewModel) {
    val barcodeResults by viewModel.barcodeResult.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    val hasCameraPermission by viewModel.hasCameraPermission.collectAsState()
    val hasStoragePermissions by viewModel.hasStoragePermission.collectAsState()

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            viewModel.permissionRepository.notifyPermissionChanged(Manifest.permission.CAMERA)
            if (!isGranted) {
                Toast
                    .makeText(
                        context,
                        "Camera permission is required for live scanning.",
                        Toast.LENGTH_SHORT
                    ).show()
            }
            viewModel.resetUiState()
        }
    LaunchedEffect(uiState) {
        when (uiState) {
            is UiState.Error -> {
                val errorMessage = (uiState as UiState.Error).message
                Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                Log.e("BarcodeScreen", "Error: $errorMessage")
                viewModel.resetUiState()
            }

            is UiState.Permission -> {
                val permissionToRequest = (uiState as UiState.Permission).permission
                when (permissionToRequest) {
                    Manifest.permission.CAMERA -> cameraPermissionLauncher.launch(
                        Manifest.permission.CAMERA
                    )
                }
                viewModel.resetUiState()
            }

            is UiState.Idle -> {
            }

            is UiState.Loading -> {
            }

            is UiState.Success -> {
            }
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // For Camera Preview
            Log.d("TAG", "BarcodeScannerScreen: $hasCameraPermission")
            if (hasCameraPermission) {
                CameraPreview(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(
                            Color.Black,
                            RoundedCornerShape(8.dp)
                        ),
                    analyzeLive = { imageProxy ->
                        viewModel.scanBarcodeLive(imageProxy = imageProxy)
                    }
                )
            } else {
                CameraPermissionPlaceholder(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp),
                    onPermissionRequested = {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                )
            }
            Spacer(modifier = Modifier.height(42.dp))
            // For Barcode Result
            if (barcodeResults.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(barcodeResults) { barcode ->
                        Text(
                            text = "Result: ${barcode.barcode.rawValue}",
                            modifier =
                            Modifier
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}
