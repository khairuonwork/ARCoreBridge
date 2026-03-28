package com.example.arcorebridge2.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.arcorebridge2.ui.viewmodel.ARCoreViewModel

@Composable
fun PoseDataCard(viewModel: ARCoreViewModel = viewModel()) {

    val state by viewModel.uiState.collectAsState()

    Card {
        Column {
            Text("Position:")
            Text(state.position)

            Text("Quaternion:")
            Text(state.rotation)
        }
    }
}