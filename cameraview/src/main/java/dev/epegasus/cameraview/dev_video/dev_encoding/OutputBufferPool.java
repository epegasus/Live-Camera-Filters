package dev.epegasus.cameraview.dev_video.dev_encoding;

import android.media.MediaCodec;
import android.os.Build;

import dev.epegasus.cameraview.dev_internal.Pool;

import androidx.annotation.RequiresApi;

/**
 * A simple {@link Pool(int, Factory)} implementation for output buffers.
 */
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
class OutputBufferPool extends Pool<OutputBuffer> {

    OutputBufferPool(final int trackIndex) {
        super(Integer.MAX_VALUE, new Factory<OutputBuffer>() {
            @Override
            public OutputBuffer create() {
                OutputBuffer buffer = new OutputBuffer();
                buffer.trackIndex = trackIndex;
                buffer.info = new MediaCodec.BufferInfo();
                return buffer;
            }
        });
    }
}
