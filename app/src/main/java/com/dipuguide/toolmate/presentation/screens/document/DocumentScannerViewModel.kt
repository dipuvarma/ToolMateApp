package com.dipuguide.toolmate.presentation.screens.document

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dipuguide.toolmate.domain.model.DocumentResultData
import com.dipuguide.toolmate.domain.repository.DocumentScannerRepo
import com.dipuguide.toolmate.presentation.common.state.UiState
import com.google.android.datatransport.cct.internal.LogEvent
import com.google.mlkit.vision.documentscanner.GmsDocumentScanner
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DocumentScannerViewModel @Inject constructor(
    private val documentScannerRepo: DocumentScannerRepo,
    private val scanner: GmsDocumentScanner,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _documentScannerResult = MutableStateFlow(DocumentResultData())
    val documentScannerResult: StateFlow<DocumentResultData> = _documentScannerResult.asStateFlow()


    fun startDocumentScan(
        activity: Activity,
        onIntentReady: (IntentSender) -> Unit,
    ) {
        scanner.getStartScanIntent(activity)
            .addOnSuccessListener { sender ->
                onIntentReady(sender)
            }.addOnFailureListener { e ->
                Log.e("TAG", "startDocumentScan: ", e)
            }
    }

    fun documentScanned(context: Context, result: GmsDocumentScanningResult?) {
        if (result == null) return
        viewModelScope.launch {
            val scannedResult = documentScannerRepo.scanDocument(context = context, result = result)
            _documentScannerResult.value = scannedResult
        }
    }

    fun resetUiState(){
        _uiState.value = UiState.Idle
    }


}