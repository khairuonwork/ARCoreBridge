/*
    - Object PoseRepository
    - Central of all data
    -
 */

package com.example.arcorebridge2.domain.repository

import com.example.arcorebridge2.domain.model.ARPose
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object PoseRepository {
    private val _poseFlow = MutableSharedFlow<ARPose>(
        replay = 0,
        extraBufferCapacity = 64
    )

    val poseFlow = _poseFlow.asSharedFlow()

    fun emitPose(pose: ARPose) {
        _poseFlow.tryEmit(pose)
    }
}
