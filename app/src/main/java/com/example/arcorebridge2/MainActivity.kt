package com.example.arcorebridge2

import android.Manifest
import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.arcorebridge2.core.renderer.ARCoreRenderer
import com.example.arcorebridge2.core.session.ARCoreSessionManager
import com.example.arcorebridge2.core.session.CameraPermissionHelper
import com.example.arcorebridge2.ui.pages.MainScreen

class MainActivity : ComponentActivity() {

    private lateinit var sessionManager: ARCoreSessionManager
    private var renderer: ARCoreRenderer? = null
    private var glSurfaceView: GLSurfaceView? = null

    private var isAuthorized by mutableStateOf(false)
    private var isStarted by mutableStateOf(false)

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            isAuthorized = granted
            if (granted) initAR()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sessionManager = ARCoreSessionManager(this)

        isAuthorized = CameraPermissionHelper.hasCameraPermission(this)

        if (!isAuthorized) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            initAR()
        }

        setContent {
            MainScreen(
                isAuthorized = isAuthorized,
                isStarted = isStarted,
                renderer = renderer,
                onStartClick = {
                    isStarted = true

                    renderer?.isActive = true
                    sessionManager.resume()
                    glSurfaceView?.onResume()
                },
                onSurfaceReady = { glSurfaceView = it }
            )
        }
    }

    private fun initAR() {
        val session = sessionManager.createSession() ?: return
        renderer = ARCoreRenderer(session)
    }

    override fun onResume() {
        super.onResume()

        if (isStarted) {
            sessionManager.resume()
            glSurfaceView?.onResume()
        }
    }

    override fun onPause() {
        super.onPause()

        glSurfaceView?.onPause()
        sessionManager.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        sessionManager.destroy()
    }
}