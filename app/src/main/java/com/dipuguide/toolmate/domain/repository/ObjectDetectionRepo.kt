package com.dipuguide.toolmate.domain.repository

import androidx.camera.core.ImageProxy
import com.dipuguide.toolmate.domain.model.ObjectDetectionModel
import com.google.mlkit.vision.objects.DetectedObject

interface ObjectDetectionRepo {

    suspend fun scanObject(imageProxy: ImageProxy): Result<List<DetectedObject>>

}