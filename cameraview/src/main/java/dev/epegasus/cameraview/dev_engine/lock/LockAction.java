package dev.epegasus.cameraview.dev_engine.lock;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import dev.epegasus.cameraview.dev_engine.action.ActionWrapper;
import dev.epegasus.cameraview.dev_engine.action.Actions;
import dev.epegasus.cameraview.dev_engine.action.BaseAction;

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
public class LockAction extends ActionWrapper {

    private final BaseAction action = Actions.together(
            new ExposureLock(),
            new FocusLock(),
            new WhiteBalanceLock()
    );

    @NonNull
    @Override
    public BaseAction getAction() {
        return action;
    }
}
