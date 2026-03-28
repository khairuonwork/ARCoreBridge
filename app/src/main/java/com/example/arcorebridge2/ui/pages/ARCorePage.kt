package com.example.arcorebridge2.ui.pages

import android.opengl.GLSurfaceView
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.*
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.arcorebridge2.core.renderer.ARCoreRenderer
import com.example.arcorebridge2.ui.components.PoseDataCard
import com.example.arcorebridge2.ui.components.TrackingStateCard

@Composable
fun ARCorePage(
    renderer: ARCoreRenderer,
    onSurfaceReady: (GLSurfaceView) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var glSurfaceView: GLSurfaceView? = remember { null }

    Box(modifier = Modifier.fillMaxSize()) {

        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0f),
            factory = { context ->
                GLSurfaceView(context).apply {
                    setEGLContextClientVersion(2)
                    setRenderer(renderer)
                    renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY

                    glSurfaceView = this
                    onSurfaceReady(this)
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            PoseDataCard()
            Spacer(modifier = Modifier.height(12.dp))
            TrackingStateCard()
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> glSurfaceView?.onResume()
                Lifecycle.Event.ON_PAUSE -> glSurfaceView?.onPause()
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}