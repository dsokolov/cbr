package me.ilich.cbr.viewmodel;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.ilich.cbr.model.Model;
import me.ilich.cbr.model.ModelService;

public abstract class ViewModelActivity extends AppCompatActivity {

    private final ModelServiceConnection serviceConnection = new ModelServiceConnection();
    private Model model;
    private boolean bound = false;
    private boolean isFirstStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!bound) {
            bindService(ModelService.intent(this), serviceConnection, BIND_AUTO_CREATE);
        }
        isFirstStart = savedInstanceState == null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bound) {
            unbindService(serviceConnection);
            bound = false;
        }

    }

    protected boolean isFirstStart() {
        return isFirstStart;
    }

    protected Model getModel() {
        return model;
    }

    protected void onBound() {

    }

    protected void onUnbound() {

    }

    private class ModelServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ModelService.LocalBinder binder = (ModelService.LocalBinder) service;
            model = binder.getService();
            bound = true;
            onBound();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
            model = null;
            onUnbound();
        }
    }

}
