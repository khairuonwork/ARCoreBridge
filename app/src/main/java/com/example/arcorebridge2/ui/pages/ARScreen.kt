package com.example.arcorebridge2.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.arcorebridge2.core.renderer.ARCoreRenderer
import com.example.arcorebridge2.ui.components.PoseDataCard
import com.example.arcorebridge2.ui.components.TrackingStateCard

@Composable
fun ARScreen(renderer: ARCoreRenderer) {
    Box {
        ARCorePage(
            renderer = renderer,
            onSurfaceReady = {}
        )

        Column() {
            PoseDataCard()
            TrackingStateCard()
        }
    }
}