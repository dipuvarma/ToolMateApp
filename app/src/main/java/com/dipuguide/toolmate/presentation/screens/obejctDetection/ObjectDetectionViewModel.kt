package com.dipuguide.toolmate.presentation.screens.obejctDetection

import android.Manifest
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dipuguide.toolmate.domain.model.ObjectDetectionModel
import com.dipuguide.toolmate.domain.repository.ObjectDetectionRepo
import com.dipuguide.toolmate.domain.repository.PermissionRepository
import com.dipuguide.toolmate.presentation.common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ObjectDetectionViewModel @Inject constructor(
    private val objectDetectionRepo: ObjectDetectionRepo,
    val permissionRepository: PermissionRepository
) : ViewModel() {

    private val _objectDetectedResult = MutableStateFlow<List<ObjectDetectionModel>>(emptyList())
    val objectDetectedResult = _objectDetectedResult.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    val hasCameraPermission = permissionRepository.cameraPermissionState

    init {
        permissionRepository.notifyPermissionChanged(Manifest.permission.CAMERA)
    }

    fun scanObjectDetection(imageProxy: ImageProxy) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = objectDetectionRepo.scanObject(imageProxy)
            result.onSuccess { detectedObjects ->
                val objectDetectedResult = detectedObjects.map { detectedObject ->
                    val label = detectedObject.labels.firstOrNull()?.text ?: "Unknown"
                    ObjectDetectionModel(detectedObject.boundingBox, label)
                }
                _objectDetectedResult.value = objectDetectedResult
                _uiState.value = UiState.Idle
            }
        }
    }

    fun resetUiState() {
        _uiState.value = UiState.Idle
    }


}