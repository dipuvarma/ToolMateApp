package com.dipuguide.toolmate.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface PermissionRepository {
    fun getStoragePermission(): String

    fun checkPermission(permission: String): Boolean

    fun notifyPermissionChanged(permission: String)

    val storagePermissionState: StateFlow<Boolean>
    val cameraPermissionState: StateFlow<Boolean>
}
