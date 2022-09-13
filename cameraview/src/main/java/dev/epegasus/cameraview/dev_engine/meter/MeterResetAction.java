package dev.epegasus.cameraview.dev_engine.meter;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import dev.epegasus.cameraview.dev_engine.action.ActionWrapper;
import dev.epegasus.cameraview.dev_engine.action.Actions;
import dev.epegasus.cameraview.dev_engine.action.BaseAction;

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public class MeterResetAction extends ActionWrapper {

    private final BaseAction action;

    public MeterResetAction() {
        this.action = Actions.together(
                new ExposureReset(),
                new FocusReset(),
                new WhiteBalanceReset()
        );
    }

    @NonNull
    @Override
    public BaseAction getAction() {
        return action;
    }
}
