package com.example.arcorebridge2.data.mapper

import com.example.arcorebridge2.domain.model.*
import com.example.arcorebridge2.data.model.*

fun ARPose.toPacket(): ARStreamPacket {
    return ARStreamPacket(
        px, py, pz,
        qx, qy, qz, qw, trackingState = trackingState.toInt()
    )
}