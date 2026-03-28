package com.example.arcorebridge2.core.session

import android.app.Activity
import android.content.Context
import com.google.ar.core.*
import com.google.ar.core.exceptions.*

class ARCoreSessionManager(
    private val activity: Activity
) {

    private var session: Session? = null

    fun createSession(): Session? {
        if (session != null) return session

        try {
            val availability = ArCoreApk.getInstance().checkAvailability(activity)
            if (!availability.isSupported) {
                throw RuntimeException("ARCore not supported")
            }

            when (ArCoreApk.getInstance().requestInstall(activity, true)) {
                ArCoreApk.InstallStatus.INSTALL_REQUESTED -> return null
                ArCoreApk.InstallStatus.INSTALLED -> {}
            }
            val newSession = Session(activity)
            val config = Config(newSession).apply {
                planeFindingMode = Config.PlaneFindingMode.HORIZONTAL
                updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
            }

            newSession.configure(config)
            session = newSession

        } catch (e: UnavailableArcoreNotInstalledException) {
            e.printStackTrace()
        } catch (e: UnavailableApkTooOldException) {
            e.printStackTrace()
        } catch (e: UnavailableSdkTooOldException) {
            e.printStackTrace()
        } catch (e: UnavailableDeviceNotCompatibleException) {
            e.printStackTrace()
        }

        return session
    }

    fun resume() {
        try {
            session?.resume()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun pause() {
        session?.pause()
    }

    fun destroy() {
        session?.close()
        session = null
    }

    fun getSession(): Session? = session
}