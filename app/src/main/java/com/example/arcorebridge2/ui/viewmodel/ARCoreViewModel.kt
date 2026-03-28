package com.example.arcorebridge2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcorebridge2.domain.repository.PoseRepository
import com.example.arcorebridge2.ui.state.ARCoreUIState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ARCoreViewModel : ViewModel() {

    val uiState = PoseRepository.poseFlow
        .map { pose ->
            ARCoreUIState(
                position = "${pose.px}, ${pose.py}, ${pose.pz}",
                rotation = "${pose.qx}, ${pose.qy}, ${pose.qz}, ${pose.qw}",
                trackingText = pose.trackingState.name
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            ARCoreUIState()
        )
}