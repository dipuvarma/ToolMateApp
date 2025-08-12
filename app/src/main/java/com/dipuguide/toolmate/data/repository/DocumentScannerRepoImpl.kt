package com.dipuguide.toolmate.data.repository

import android.content.Context
import com.dipuguide.toolmate.domain.model.DocumentResultData
import com.dipuguide.toolmate.domain.repository.DocumentScannerRepo
import com.google.mlkit.vision.documentscanner.GmsDocumentScanner
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DocumentScannerRepoImpl @Inject constructor(
    private val documentScanner: GmsDocumentScanner,
) : DocumentScannerRepo {
    override suspend fun scanDocument(
        context: Context,
        result: GmsDocumentScanningResult,
    ): DocumentResultData = withContext(Dispatchers.IO) {
        val image = result.pages?.mapNotNull {
            it.imageUri
        } ?: emptyList()
        val pdf = result.pdf?.let {
            it.uri
        }
        DocumentResultData(imageUris = image, pdfUri = pdf)
    }
}