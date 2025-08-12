package com.dipuguide.toolmate.domain.repository

import android.content.Context
import com.dipuguide.toolmate.domain.model.DocumentResultData
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult

interface DocumentScannerRepo {

    suspend fun scanDocument(context: Context, result: GmsDocumentScanningResult): DocumentResultData

}