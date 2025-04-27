package com.example.findapic.ui.commons.header

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Header(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(16.dp),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary,
    )
}