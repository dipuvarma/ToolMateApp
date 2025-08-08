package com.dipuguide.toolmate.presentation.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dipuguide.toolmate.domain.model.ToolCardItemModel
import com.dipuguide.toolmate.presentation.ui.theme.ToolMateTheme
import com.dipuguide.toolmate.utils.Dimen


@Composable
fun ToolCardItemWidget(
    toolCardItem: ToolCardItemModel,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        onClick = {
            toolCardItem.onClick()
        },
        elevation = CardDefaults.elevatedCardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(Dimen.PaddingSmall),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(Dimen.IconSizeExtraLarge),
                painter = toolCardItem.image,
                contentDescription = toolCardItem.imageDescription,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
            )
            Spacer(modifier = Modifier.height(Dimen.SpacerSmall))
            Text(
                text = toolCardItem.title,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }

    }

}