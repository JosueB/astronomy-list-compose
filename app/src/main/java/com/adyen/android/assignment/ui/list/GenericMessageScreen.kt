package com.adyen.android.assignment.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adyen.android.assignment.R

@Composable
fun GenericMessageScreen(
    onRefreshClick: () -> Unit
) {
    // Use a Box to center the content
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            // Error icon
            Icon(
                painter = painterResource(id = R.drawable.ic_vector), // Update with your own vector drawable
                contentDescription = "Error Icon",
                tint = Color.Red,
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 24.dp), // Adjust size and padding
            )
            Spacer(modifier = Modifier.padding(30.dp))

            // Error message text
            Text(
                text = "Oops! Something went wrong.",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall, // Material 3's headlineSmall style
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Error description text
            Text(
                text = "Please refresh the page and try again.",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium, // Material 3's bodyMedium style
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Refresh button using Material 3 Button
            Button(
                onClick = onRefreshClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Refresh", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Composable
@Preview
fun GenericMessageScreenPreview() {
    GenericMessageScreen { }
}

