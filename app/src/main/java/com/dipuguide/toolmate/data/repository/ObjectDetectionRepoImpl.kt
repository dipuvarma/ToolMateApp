package com.dipuguide.toolmate.data.repository

import androidx.camera.core.ImageProxy
import com.dipuguide.toolmate.domain.model.ObjectDetectionModel
import com.dipuguide.toolmate.domain.repository.ObjectDetectionRepo
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetector
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ObjectDetectionRepoImpl @Inject constructor(
    private val objectDetector: ObjectDetector,
) : ObjectDetectionRepo {

    override suspend fun scanObject(imageProxy: ImageProxy): Result<List<DetectedObject>> {
        val mediaImage = imageProxy.image
        if (mediaImage == null) {
            imageProxy.close()
            return Result.failure(Throwable("Media Image not found"))
        }
        val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        return try {
            val detectedObject = objectDetector.process(inputImage).await()
            Result.success(detectedObject)

        } catch (e: Exception) {
            Result.failure(e)
        } finally {
            imageProxy.close()
        }
    }

}