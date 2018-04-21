package de.cdvost.jibjib.threading;

import android.os.Handler;
import android.os.Looper;

import de.cdvost.jibjib.domain.executor.MainThread;

/**
 * This class makes sure that the runnable we provide will be run on the main UI thread.
 */
public class MainThreadImpl implements MainThread {

    private static MainThread mainThread;

    private Handler handler;

    private MainThreadImpl() {

        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable){
        handler.post(runnable);
    }

    public static MainThread getInstance() {
        if (mainThread == null) {
            mainThread = new MainThreadImpl();
        }
        return mainThread;
    }
}
