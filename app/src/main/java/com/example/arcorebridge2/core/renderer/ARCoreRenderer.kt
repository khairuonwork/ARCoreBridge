package com.example.arcorebridge2.core.renderer

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.example.arcorebridge2.domain.repository.PoseRepository
import com.example.arcorebridge2.domain.model.ARPose
import com.example.arcorebridge2.domain.model.TrackingState
import com.google.ar.core.Frame
import com.google.ar.core.Session
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import com.google.ar.core.TrackingState as ARTrackingState

class ARCoreRenderer(
    private val session: Session,
    var isActive: Boolean = false
) : GLSurfaceView.Renderer {

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        val textures = IntArray(1)
        GLES20.glGenTextures(1, textures, 0)
        val textureId = textures[0]

        session.setCameraTextureName(textureId)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {}

    override fun onDrawFrame(gl: GL10?) {
        if (!isActive) return
        try {
            val frame = session.update()
            val pose = extractPoseFromARCore(frame)
            PoseRepository.emitPose(pose)

        } catch (e: Exception) {
            return
        }
    }

    private fun extractPoseFromARCore(frame: Frame): ARPose {
        val camera = frame.camera
        val pose = camera.pose

        val t = pose.translation
        val r = pose.rotationQuaternion

        val trackingState = when (camera.trackingState) {
            ARTrackingState.TRACKING -> TrackingState.TRACKING
            ARTrackingState.PAUSED -> TrackingState.PAUSED
            ARTrackingState.STOPPED -> TrackingState.STOPPED
        }

        return ARPose(
            px = t[0],
            py = t[1],
            pz = t[2],
            qx = r[0],
            qy = r[1],
            qz = r[2],
            qw = r[3],
            trackingState = trackingState
        )
    }
}