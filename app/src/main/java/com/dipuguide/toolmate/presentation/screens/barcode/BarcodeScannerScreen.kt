package com.dipuguide.toolmate.presentation.screens.barcode

import android.Manifest
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dipuguide.toolmate.R
import com.dipuguide.toolmate.presentation.common.state.UiState
import com.dipuguide.toolmate.presentation.navigation.Home
import com.dipuguide.toolmate.presentation.navigation.LocalNavController
import com.dipuguide.toolmate.presentation.navigation.MainRoute
import com.dipuguide.toolmate.utils.CameraPermissionPlaceholder
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarcodeScannerScreen(viewModel: BarcodeScannerViewModel) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()
    val barcodeResults by viewModel.barcodeResult.collectAsState()
    val hasCameraPermission by viewModel.hasCameraPermission.collectAsState()
    val hasStoragePermissions by viewModel.hasStoragePermission.collectAsState()
    val navController = LocalNavController.current


    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            viewModel.permissionRepository.notifyPermissionChanged(Manifest.permission.CAMERA)
            if (!isGranted) {
                Toast.makeText(
                    context,
                    "Camera permission is required for live scanning.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            viewModel.resetUiState()
        }


    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { imageUri ->
        imageUri?.let { uri ->
            val takeFlag: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION

            try {
                context.contentResolver.takePersistableUriPermission(uri, takeFlag)

            } catch (e: Exception) {

            }
            coroutineScope.launch(Dispatchers.IO) {
                try {
                    val bitmap = ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(
                            context.contentResolver,
                            uri
                        )
                    )
                    val inputImage = InputImage.fromBitmap(bitmap, 0)
                    withContext(Dispatchers.Main) {
                        viewModel.scanBarcodeStatic(inputImage, uri)
                    }
                } catch (e: Exception) {
                    Log.e("BarcodeScreen", "Error decoding image from gallery: ${e.message}", e)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Failed to load image: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }

    val storagePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.permissionRepository.notifyPermissionChanged(viewModel.permissionRepository.getStoragePermission())
        if (isGranted) {
            photoPickerLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Storage Permission is required for Image", Toast.LENGTH_SHORT)
                .show()
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

                    viewModel.permissionRepository.getStoragePermission() -> storagePermissionLauncher.launch(
                        viewModel.permissionRepository.getStoragePermission()
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Barcode Scanner",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(MainRoute)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Navigation"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (hasStoragePermissions) {
                                photoPickerLauncher.launch("image/*")
                            } else {
                                storagePermissionLauncher.launch(viewModel.permissionRepository.getStoragePermission())
                                Log.d(
                                    "TAG",
                                    "BarcodeScannerScreen: ${viewModel.permissionRepository.getStoragePermission()}"
                                )
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.image_icon),
                            contentDescription = "Image Upload Icon"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // For Camera Preview
            if (hasCameraPermission) {
                CameraPreview(
                    modifier = Modifier
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
                    modifier = Modifier
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
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}
