package com.dipuguide.toolmate.presentation.screens.obejctDetection

import android.Manifest
import android.graphics.Paint
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.dipuguide.toolmate.domain.model.ObjectDetectionModel
import com.dipuguide.toolmate.presentation.navigation.LocalNavController
import com.dipuguide.toolmate.presentation.screens.barcode.CameraPreview
import com.dipuguide.toolmate.utils.CameraPermissionPlaceholder

@Composable
fun ObjectDetectionScreen() {

    val viewModel = hiltViewModel<ObjectDetectionViewModel>()
    val context = LocalContext.current
    val objectDetectedResult by viewModel.objectDetectedResult.collectAsState()
    val hasCameraPermission by viewModel.hasCameraPermission.collectAsState()

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
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


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (hasCameraPermission) {
            CameraPreview(
                cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            ) { imageProxy ->
                viewModel.scanObjectDetection(imageProxy)
            }
            ObjectOverlay(objects = objectDetectedResult)
        } else {
            CameraPermissionPlaceholder() {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }


}


@Composable
fun ObjectOverlay(objects: List<ObjectDetectionModel>) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val textPaint = Paint().apply {
            color = Color.White.toArgb()
            textSize = 40f
        }

        objects.forEach { detectedObject ->
            // Draw bounding box
            drawRect(
                color = Color.Red,
                topLeft = androidx.compose.ui.geometry.Offset(
                    detectedObject.boundingBox.left.toFloat(),
                    detectedObject.boundingBox.top.toFloat()
                ),
                size = androidx.compose.ui.geometry.Size(
                    detectedObject.boundingBox.width().toFloat(),
                    detectedObject.boundingBox.height().toFloat()
                ),
                style = Stroke(width = 2f)
            )

            // Draw label
            drawContext.canvas.nativeCanvas.drawText(
                detectedObject.label,
                detectedObject.boundingBox.left.toFloat(),
                detectedObject.boundingBox.top.toFloat() - 10f, // Position text above the box
                textPaint
            )
        }
    }
}