package com.adyen.android.assignment.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
            .fillMaxSize()
            .background(Color.Black), // Background color (can also use MaterialTheme.colorScheme.background for dark mode handling)
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
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 24.dp), // Adjust size and padding
                tint = Color(0xFF7209B7) // Purple color for the icon (use Material3's color scheme if needed)
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
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7209B7)), // Purple color for the button
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

