package com.dipuguide.toolmate.domain.repository

import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

interface BarcodeRepository {
    suspend fun scanBarcodeLive(imageProxy: ImageProxy): Result<List<Barcode>>

    suspend fun scanBarcodeStatic(inputImage: InputImage): Result<List<Barcode>>
}
