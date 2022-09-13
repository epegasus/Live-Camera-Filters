package dev.epegasus.cameraview.dev_filter;

import androidx.annotation.NonNull;

import dev.epegasus.cameraview.CameraView;
import dev.epegasus.cameraview.dev_filters.AutoFixFilter;
import dev.epegasus.cameraview.dev_filters.BlackAndWhiteFilter;
import dev.epegasus.cameraview.dev_filters.BrightnessFilter;
import dev.epegasus.cameraview.dev_filters.ContrastFilter;
import dev.epegasus.cameraview.dev_filters.CrossProcessFilter;
import dev.epegasus.cameraview.dev_filters.DocumentaryFilter;
import dev.epegasus.cameraview.dev_filters.DuotoneFilter;
import dev.epegasus.cameraview.dev_filters.FillLightFilter;
import dev.epegasus.cameraview.dev_filters.GammaFilter;
import dev.epegasus.cameraview.dev_filters.GrainFilter;
import dev.epegasus.cameraview.dev_filters.GrayscaleFilter;
import dev.epegasus.cameraview.dev_filters.HueFilter;
import dev.epegasus.cameraview.dev_filters.InvertColorsFilter;
import dev.epegasus.cameraview.dev_filters.LomoishFilter;
import dev.epegasus.cameraview.dev_filters.PosterizeFilter;
import dev.epegasus.cameraview.dev_filters.SaturationFilter;
import dev.epegasus.cameraview.dev_filters.SepiaFilter;
import dev.epegasus.cameraview.dev_filters.SharpnessFilter;
import dev.epegasus.cameraview.dev_filters.TemperatureFilter;
import dev.epegasus.cameraview.dev_filters.TintFilter;
import dev.epegasus.cameraview.dev_filters.VignetteFilter;

/**
 * Contains commonly used {@link Filter}s.
 *
 * You can use {@link #newInstance()} to create a new instance and
 * pass it to {@link CameraView#setFilter(Filter)}.
 */
public enum Filters {

    /** @see NoFilter */
    NONE(NoFilter.class),

    /** @see AutoFixFilter */
    AUTO_FIX(AutoFixFilter.class),

    /** @see BlackAndWhiteFilter */
    BLACK_AND_WHITE(BlackAndWhiteFilter.class),

    /** @see BrightnessFilter */
    BRIGHTNESS(BrightnessFilter.class),

    /** @see ContrastFilter */
    CONTRAST(ContrastFilter.class),

    /** @see CrossProcessFilter */
    CROSS_PROCESS(CrossProcessFilter.class),

    /** @see DocumentaryFilter */
    DOCUMENTARY(DocumentaryFilter.class),

    /** @see DuotoneFilter */
    DUOTONE(DuotoneFilter.class),

    /** @see FillLightFilter */
    FILL_LIGHT(FillLightFilter.class),

    /** @see GammaFilter */
    GAMMA(GammaFilter.class),

    /** @see GrainFilter */
    GRAIN(GrainFilter.class),

    /** @see GrayscaleFilter */
    GRAYSCALE(GrayscaleFilter.class),

    /** @see HueFilter */
    HUE(HueFilter.class),

    /** @see InvertColorsFilter */
    INVERT_COLORS(InvertColorsFilter.class),

    /** @see LomoishFilter */
    LOMOISH(LomoishFilter.class),

    /** @see PosterizeFilter */
    POSTERIZE(PosterizeFilter.class),

    /** @see SaturationFilter */
    SATURATION(SaturationFilter.class),

    /** @see SepiaFilter */
    SEPIA(SepiaFilter.class),

    /** @see SharpnessFilter */
    SHARPNESS(SharpnessFilter.class),

    /** @see TemperatureFilter */
    TEMPERATURE(TemperatureFilter.class),

    /** @see TintFilter */
    TINT(TintFilter.class),

    /** @see VignetteFilter */
    VIGNETTE(VignetteFilter.class);

    private Class<? extends Filter> filterClass;

    Filters(@NonNull Class<? extends Filter> filterClass) {
        this.filterClass = filterClass;
    }

    /**
     * Returns a new instance of the given filter.
     * @return a new instance
     */
    @NonNull
    public Filter newInstance() {
        try {
            return filterClass.newInstance();
        } catch (IllegalAccessException e) {
            return new NoFilter();
        } catch (InstantiationException e) {
            return new NoFilter();
        }
    }
}
