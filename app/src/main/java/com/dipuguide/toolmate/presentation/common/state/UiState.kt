package com.dipuguide.toolmate.presentation.common.state

sealed class UiState {
    object Idle : UiState()

    object Loading : UiState()

    data class Success(
        val data: String
    ) : UiState()

    data class Error(
        val message: String
    ) : UiState()

    data class Permission(
        val permission: String
    ) : UiState()
}
