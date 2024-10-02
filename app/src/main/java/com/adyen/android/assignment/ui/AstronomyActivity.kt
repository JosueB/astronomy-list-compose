package com.adyen.android.assignment.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.adyen.android.assignment.ui.ui.theme.AssignmentTheme


class AstronomyActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: AstronomyViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            AssignmentTheme {
                AstronomyApp(viewModel)
            }
        }
    }
}