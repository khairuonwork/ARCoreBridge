package com.example.arcorebridge2.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun StartCard(onClick: () -> Unit) {
    Card {
        Column {
            Text("AR Pose Tracking")

            Button(onClick = onClick) {
                Text("Start Tracking Pose Data")
            }
        }
    }
}