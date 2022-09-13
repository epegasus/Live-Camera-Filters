package dev.epegasus.cameraview.dev_markers;

import dev.epegasus.cameraview.CameraView;
import dev.epegasus.cameraview.dev_gesture.GestureAction;

/**
 * Gives information about what triggered the autofocus operation.
 */
public enum AutoFocusTrigger {

    /**
     * Autofocus was triggered by {@link GestureAction#AUTO_FOCUS}.
     */
    GESTURE,

    /**
     * Autofocus was triggered by the {@link CameraView#startAutoFocus(float, float)} method.
     */
    METHOD
}
