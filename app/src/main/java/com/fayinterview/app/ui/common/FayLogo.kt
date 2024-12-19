package com.fayinterview.app.ui.common

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FayLogo(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    size: Dp = 20.dp
) {
    // Note: Ideally we would use the actual logo instead of just this material icon
    Icon(
        Icons.Filled.WbSunny,
        contentDescription = contentDescription,
        tint = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .size(size)
    )
}
