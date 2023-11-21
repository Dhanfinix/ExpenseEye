package com.dhandev.rekapin.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable


object AnimUtil {
    @Composable
    fun AnimatedVisibility(
        visible: Boolean,
        content: @Composable () -> Unit
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(durationMillis = 200)),
            exit = fadeOut(animationSpec = tween(durationMillis = 200))
        ) {
            content()
        }
    }
}
