/*
    Pose = Position + Rotation
    Position = p
    Rotation (Quaternion) = q
 */

package com.example.arcorebridge2.domain.model

data class ARPose(
    val px: Float,
    val py: Float,
    val pz: Float,
    val qx: Float,
    val qy: Float,
    val qz: Float,
    val qw: Float,
    val trackingState: TrackingState
)
enum class TrackingState(val value: Int){
    // trackingState encoded as ordinal (0=TRACKING, 1=PAUSED, 2=STOPPED)

    TRACKING(0),
    PAUSED(1),
    STOPPED(2)
}

fun TrackingState.toInt(): Int = value

