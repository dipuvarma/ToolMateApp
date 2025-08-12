package com.dipuguide.toolmate.domain.model

import android.net.Uri

data class DocumentResultData(
    val imageUris: List<Uri> = emptyList(),
    val pdfUri: Uri? = null
)
