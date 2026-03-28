package com.example.arcorebridge2.ui.pages

import android.opengl.GLSurfaceView
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.arcorebridge2.core.renderer.ARCoreRenderer
import com.example.arcorebridge2.ui.components.StartCard

@Composable
fun MainScreen(
    isAuthorized: Boolean,
    isStarted: Boolean,
    renderer: ARCoreRenderer?,
    onStartClick: () -> Unit,
    onSurfaceReady: (GLSurfaceView) -> Unit
) {
    when {
        !isAuthorized -> {
            Text("Camera permission required")
        }

        !isStarted -> {
            StartCard(onStartClick)
        }

        renderer != null -> {
            ARCorePage(
                renderer = renderer,
                onSurfaceReady = onSurfaceReady
            )
        }
    }
}