package dev.epegasus.livecamerafilters.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import dev.epegasus.cameraview.dev_controls.Facing
import dev.epegasus.cameraview.dev_controls.Mode
import dev.epegasus.cameraview.dev_controls.Preview
import dev.epegasus.cameraview.dev_filter.Filters
import dev.epegasus.cameraview.dev_frame.Frame
import dev.epegasus.cameraview.dev_frame.FrameProcessor
import dev.epegasus.livecamerafilters.R
import dev.epegasus.livecamerafilters.databinding.FragmentHomeBinding
import dev.epegasus.livecamerafilters.helper.extensions.FragmentExtensions.showToast
import dev.epegasus.livecamerafilters.helper.utils.GeneralUtils.TAG
import dev.epegasus.cameraview.CameraException
import dev.epegasus.cameraview.CameraListener
import dev.epegasus.cameraview.CameraOptions
import dev.epegasus.cameraview.PictureResult
import java.io.ByteArrayOutputStream

class FragmentHome : BaseFragment<FragmentHomeBinding>() {


    companion object {
        private val REQUIRED_PERMISSIONS = mutableListOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE).toTypedArray()
        private const val USE_FRAME_PROCESSOR = false
        private const val DECODE_BITMAP = false
    }

    private var captureTime: Long = 0
    private var currentFilter = 0
    private val allFilters = Filters.values()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return getView(inflater, container, R.layout.fragment_home)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkMultiplePermissions()

        // Set up the listeners for take photo and video capture buttons
        binding.ifvCaptureHome.setOnClickListener { onCaptureClick() }
        binding.ifvRotateHome.setOnClickListener { toggleCamera() }
        binding.ifvFiltersHome.setOnClickListener { filterCamera() }
    }

    private fun checkMultiplePermissions() {
        // Request camera permissions
        if (allPermissionsGranted()) letsStart()
        else permissionResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(globalContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private val permissionResultLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        it.values.forEach { single ->
            if (!single) {
                showToast("Permission Required")
                return@forEach
            }
            letsStart()
        }
    }

    private fun letsStart() {
        setCameraConfigs()
    }

    private fun setCameraConfigs() {
        binding.cvCameraHome.apply {
            setLifecycleOwner(viewLifecycleOwner)
            addCameraListener(Listener())
            open()

            if (USE_FRAME_PROCESSOR) {
                addFrameProcessor(object : FrameProcessor {
                    private var lastTime = System.currentTimeMillis()
                    override fun process(frame: Frame) {
                        val newTime = frame.time
                        val delay = newTime - lastTime
                        lastTime = newTime
                        Log.d(TAG, "process: Frame delayMillis:" + delay + "FPS:" + 1000 / delay)
                        if (DECODE_BITMAP) {
                            if (frame.format == ImageFormat.NV21 && frame.dataClass == ByteArray::class.java) {
                                val data = frame.getData<ByteArray>()
                                val yuvImage = YuvImage(data, frame.format, frame.size.width, frame.size.height, null)
                                val jpegStream = ByteArrayOutputStream()
                                yuvImage.compressToJpeg(Rect(0, 0, frame.size.width, frame.size.height), 100, jpegStream)
                                val jpegByteArray = jpegStream.toByteArray()
                                val bitmap = BitmapFactory.decodeByteArray(jpegByteArray, 0, jpegByteArray.size)
                                bitmap.toString()
                            }
                        }
                    }
                })
            }
        }
    }


    private inner class Listener : CameraListener() {
        override fun onCameraOpened(options: CameraOptions) {
            /*val group = controlPanel.getChildAt(0) as ViewGroup
            for (i in 0 until group.childCount) {
                val view = group.getChildAt(i) as OptionView<*>
                view.onCameraOpened(camera, options)
            }*/
        }

        override fun onCameraError(exception: CameraException) {
            super.onCameraError(exception)
            showToast("Got CameraException #" + exception.reason)
        }

        override fun onPictureTaken(result: PictureResult) {
            super.onPictureTaken(result)
            if (binding.cvCameraHome.isTakingVideo) {
                showToast("Captured while taking video. Size=" + result.size)
                return
            }

            // This can happen if picture was taken with a gesture.
            Log.d(TAG, "onPictureTaken: launched")
            val callbackTime = System.currentTimeMillis()
            if (captureTime == 0L) captureTime = callbackTime - 300
            Log.d(TAG, "onPictureTaken called! Launching activity. Delay: ${callbackTime - captureTime}")
            captureTime = 0
        }

        override fun onVideoRecordingStart() {
            super.onVideoRecordingStart()
            Log.d(TAG, "onVideoRecordingStart: called")
        }

        override fun onVideoRecordingEnd() {
            super.onVideoRecordingEnd()
            showToast("Video taken. Processing...")
            Log.d(TAG, "onVideoRecordingEnd: called")
        }

        override fun onExposureCorrectionChanged(newValue: Float, bounds: FloatArray, fingers: Array<PointF>?) {
            super.onExposureCorrectionChanged(newValue, bounds, fingers)

        }

        override fun onZoomChanged(newValue: Float, bounds: FloatArray, fingers: Array<PointF>?) {
            super.onZoomChanged(newValue, bounds, fingers)

        }
    }


    private fun onCaptureClick() {
        binding.cvCameraHome.apply {
            if (mode == Mode.VIDEO) return run {
                showToast("Can't take HQ pictures while in VIDEO mode.")
            }
            if (isTakingPicture) return
            captureTime = System.currentTimeMillis()
            showToast("Capturing picture...")
            takePicture()
        }
    }

    private fun toggleCamera() {
        binding.cvCameraHome.apply {
            if (isTakingPicture) return
            when (toggleFacing()) {
                Facing.BACK -> showToast("Switched to back camera!")
                Facing.FRONT -> showToast("Switched to front camera!")
            }
        }
    }


    private fun filterCamera() {
        binding.cvCameraHome.apply {
            if (preview != Preview.GL_SURFACE) return run {
                showToast("Filters are supported only when preview is Preview.GL_SURFACE.")
            }
            if (currentFilter < allFilters.size - 1) {
                currentFilter++
            } else {
                currentFilter = 0
            }
            val filter = allFilters[currentFilter]
            showToast(filter.toString())

            // Normal behavior:
            this.filter = filter.newInstance()
        }
    }
}