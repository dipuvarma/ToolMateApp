package com.dipuguide.toolmate.domain.model

import android.net.Uri
import com.google.mlkit.vision.barcode.common.Barcode

data class ScannedBarcode(
    val barcode: Barcode,
    val imageUri: Uri? = null
)
