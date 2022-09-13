package dev.epegasus.cameraview.dev_preview;


import androidx.annotation.NonNull;

import dev.epegasus.cameraview.dev_filter.Filter;


/**
 * A preview that support GL filters defined through the {@link Filter} interface.
 *
 * The preview has the responsibility of calling {@link Filter#setSize(int, int)}
 * whenever the preview size changes and as soon as the filter is applied.
 */
public interface FilterCameraPreview {

    /**
     * Sets a new filter.
     * @param filter new filter
     */
    void setFilter(@NonNull Filter filter);

    /**
     * Returns the currently used filter.
     * @return currently used filter
     */
    @SuppressWarnings("unused")
    @NonNull
    Filter getCurrentFilter();
}
