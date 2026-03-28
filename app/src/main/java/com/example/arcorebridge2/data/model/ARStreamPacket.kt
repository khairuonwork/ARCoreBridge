package com.example.arcorebridge2.data.model


data class ARStreamPacket(
    val px: Float,
    val py: Float,
    val pz: Float,
    val qx: Float,
    val qy: Float,
    val qz: Float,
    val qw: Float,

    // trackingState encoded as ordinal (0=TRACKING, 1=PAUSED, 2=STOPPED)
    val trackingState: Int
)