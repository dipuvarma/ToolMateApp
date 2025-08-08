package com.dipuguide.toolmate.domain.model

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class ToolCardItemModel(
    val id: Int,
    val title: String,
    val image: Painter,
    val imageDescription: String,
    val onClick: () -> Unit,
)