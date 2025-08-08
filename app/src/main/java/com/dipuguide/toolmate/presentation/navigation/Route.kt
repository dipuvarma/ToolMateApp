package com.dipuguide.toolmate.presentation.navigation

import kotlinx.serialization.Serializable


@Serializable
object MainRoute

@Serializable
object Home {
    const val route = "home"
}

@Serializable
object Bookmark {
    const val route = "bookmark"
}

@Serializable
object Setting {
    const val route = "setting"
}



@Serializable
object BarcodeScannerRoute



@Serializable
object DocumentScannerRoute


@Serializable
object ObjectDetectionRoute

@Serializable
object ImageLabelingRoute

@Serializable
object TextRecognitionRoute

@Serializable
object SpeechToTextRoute





