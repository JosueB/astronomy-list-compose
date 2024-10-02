package com.adyen.android.assignment.ui.list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.adyen.android.assignment.R

@Composable
fun ReorderListFab(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White,
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Icon(
                painter = painterResource(R.drawable.ic_reorder),
                contentDescription = "Reorder list"
            )
            Text(text = "Reorder List")
        }

    }
}
