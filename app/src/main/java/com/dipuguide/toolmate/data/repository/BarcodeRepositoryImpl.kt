package com.dipuguide.toolmate.data.repository

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import com.dipuguide.toolmate.domain.repository.BarcodeRepository
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@OptIn(ExperimentalGetImage::class)
class BarcodeRepositoryImpl @Inject constructor(
    val scanner: BarcodeScanner,
) : BarcodeRepository {

    // Suspend function to scan barcodes from ImageProxy
    override suspend fun scanBarcodeLive(imageProxy: ImageProxy): Result<List<Barcode>> {
        val mediaImage = imageProxy.image ?: run {
            imageProxy.close()
            return Result.failure(Throwable("MediaImage not found"))
        }
        // Create InputImage with rotation degrees
        val inputImage =
            InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        return try {
            val barcodes = scanner.process(inputImage).await()
            Result.success(barcodes)
        } catch (e: Exception) {
            Result.failure(e)
        } finally {
            imageProxy.close()
        }
    }


    override suspend fun scanBarcodeStatic(uri: String?) {
        TODO("Not yet implemented")
    }
}