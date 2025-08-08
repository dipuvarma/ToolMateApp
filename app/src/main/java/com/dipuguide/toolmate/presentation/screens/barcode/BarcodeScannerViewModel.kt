package com.dipuguide.toolmate.presentation.screens.barcode

import android.Manifest
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dipuguide.toolmate.domain.model.ScannedBarcode
import com.dipuguide.toolmate.domain.repository.BarcodeRepository
import com.dipuguide.toolmate.domain.repository.PermissionRepository
import com.dipuguide.toolmate.presentation.common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class BarcodeScannerViewModel
@Inject
constructor(
    private val barcodeRepository: BarcodeRepository,
    val permissionRepository: PermissionRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _barcodeResult = MutableStateFlow<List<ScannedBarcode>>(emptyList())
    val barcodeResult = _barcodeResult.asStateFlow()

    val hasCameraPermission = permissionRepository.cameraPermissionState
    val hasStoragePermission = permissionRepository.storagePermissionState

    init {
        permissionRepository.notifyPermissionChanged(Manifest.permission.CAMERA)
        permissionRepository.notifyPermissionChanged(
            permissionRepository.getStoragePermission()
        )
    }

    fun scanBarcodeLive(imageProxy: ImageProxy) {
        if (!hasCameraPermission.value) {
            _uiState.value = UiState.Permission(Manifest.permission.CAMERA)
            imageProxy.close()
            return
        }

        viewModelScope.launch {
            val result = barcodeRepository.scanBarcodeLive(imageProxy)
            result.onSuccess { barcodes ->
                val newScannedBarcodes =
                    barcodes.map { barcode ->
                        ScannedBarcode(barcode, null)
                    }
                val updatedList =
                    (_barcodeResult.value + newScannedBarcodes).distinctBy { it.barcode.rawValue }
                _barcodeResult.value = updatedList
            }
            result.onFailure { e ->
                _uiState.value = UiState.Error("Live scanning failed: ${e.message}")
            }
        }
    }

    fun resetUiState() {
        _uiState.value = UiState.Idle
    }
}
