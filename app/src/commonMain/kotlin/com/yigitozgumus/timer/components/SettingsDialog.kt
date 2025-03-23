package com.yigitozgumus.timer.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yigitozgumus.timer.domain.Settings

@Composable
fun SettingsDialog(
    settings: Settings,
    onDismiss: () -> Unit,
    onSettingsChange: (Settings) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Settings") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Sound Effects")
                    Switch(
                        checked = settings.isSoundEnabled,
                        onCheckedChange = {
                            onSettingsChange(settings.copy(isSoundEnabled = it))
                        }
                    )
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Close") } }
    )
}
