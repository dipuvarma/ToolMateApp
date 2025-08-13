package com.dipuguide.toolmate.data.repository

import android.content.Context
import android.net.Uri
import com.dipuguide.toolmate.domain.model.DocumentResultData
import com.dipuguide.toolmate.domain.repository.DocumentScannerRepo
import com.google.mlkit.vision.documentscanner.GmsDocumentScanner
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class DocumentScannerRepoImpl @Inject constructor() : DocumentScannerRepo {
    override suspend fun scanDocument(
        context: Context,
        result: GmsDocumentScanningResult,
    ): DocumentResultData = withContext(Dispatchers.IO) {
        val image = result.pages?.mapNotNull {
            copyUriToStorage(context, it.imageUri, "scan_page_${System.currentTimeMillis()}}.jpg")
        } ?: emptyList()
        val pdf = result.pdf?.let {
            copyUriToStorage(context, it.uri, "scan_doc_${System.currentTimeMillis()}.pdf")
        }
        DocumentResultData(imageUris = image, pdfUri = pdf)
    }

    override suspend fun copyUriToStorage(
        context: Context,
        source: Uri,
        fileName: String,
    ): Uri? = withContext(Dispatchers.IO) {
        try {
            context.contentResolver.openInputStream(source)?.use { inputStream ->
                val outputFile = File(
                    context.getExternalFilesDir(null),
                    fileName
                )
                FileOutputStream(outputFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
                Uri.fromFile(outputFile)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}