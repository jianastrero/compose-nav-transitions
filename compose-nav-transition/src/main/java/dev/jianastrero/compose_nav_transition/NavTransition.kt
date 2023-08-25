package dev.jianastrero.compose_nav_transition

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect

internal object NavTransition {
    var hostOffset = Offset.Zero
    private var _currentElements: Map<String, Rect> by mutableStateOf(mapOf())
    private var _previousElements: Map<String, Rect> by mutableStateOf(mapOf())

    val currentElements: Map<String, Rect>
        get() = _currentElements

    fun clearCurrentElements() {
        _previousElements = _currentElements
        _currentElements = mapOf()
    }

    fun addToCurrentElement(tag: String, rect: Rect) {
        Log.d("JIANDDEBUG", "adding new element: $tag -> $rect")
        _currentElements = _currentElements + (tag to rect)
    }
}

