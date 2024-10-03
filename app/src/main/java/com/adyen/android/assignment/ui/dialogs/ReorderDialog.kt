package com.adyen.android.assignment.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ReorderDialog(
    onDismissRequest: () -> Unit,
    onSelect: (ReorderOption) -> Unit,
    currentSelection: ReorderOption
) {
    var selectedOption by remember { mutableStateOf(currentSelection) }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Reorder list",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Radio buttons for ordering options
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        RadioButton(
                            selected = selectedOption == ReorderOption.BY_TITLE,
                            onClick = { selectedOption = ReorderOption.BY_TITLE }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Reorder by title")
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        RadioButton(
                            selected = selectedOption == ReorderOption.BY_DATE,
                            onClick = { selectedOption = ReorderOption.BY_DATE }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Reorder by date")
                    }
                }

                // Apply and Reset buttons
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onSelect(selectedOption) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Apply")
                }

                TextButton(
                    onClick = { onSelect(ReorderOption.RESET) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Reset")
                }
            }
        }
    }
}

enum class ReorderOption {
    BY_TITLE, BY_DATE, RESET
}
