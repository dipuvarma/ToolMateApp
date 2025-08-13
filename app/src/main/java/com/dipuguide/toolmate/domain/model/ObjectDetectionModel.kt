package com.dipuguide.toolmate.domain.model

import android.graphics.Rect

data class ObjectDetectionModel(
    val boundingBox: Rect,
    val label:String
)
