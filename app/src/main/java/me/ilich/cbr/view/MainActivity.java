package me.ilich.cbr.view;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import me.ilich.cbr.R;
import me.ilich.cbr.model.ModelService;

public class MainActivity extends AppCompatActivity implements LoadFailFragmet.Callback {

    private final ModelServiceConnection serviceConnection = new ModelServiceConnection();
    private ModelService modelService;
    private boolean bound = false;
    private LocalBroadcastReceiver broadcastReceiver = new LocalBroadcastReceiver();

    private ConverterFragment converterFragment;
    private boolean isFirstStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, ModelService.intentFilter());
        bindService(ModelService.intent(this), serviceConnection, BIND_AUTO_CREATE);
        isFirstStart = savedInstanceState == null;
        if (savedInstanceState != null) {
            Fragment contentFragment = getSupportFragmentManager().findFragmentById(R.id.container_content);
            if (contentFragment instanceof ConverterFragment) {
                converterFragment = (ConverterFragment) contentFragment;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bound) {
            unbindService(serviceConnection);
            bound = false;
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    private void onBound() {
        if (isFirstStart) {
            modelService.loadValutes();
        }
    }

    @Override
    public void onRetryLoading() {
        modelService.loadValutes();
    }

    private class ModelServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ModelService.LocalBinder binder = (ModelService.LocalBinder) service;
            modelService = binder.getService();
            bound = true;
            onBound();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    }

    private class LocalBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ModelService.ACTION_LOADING)) {
                converterFragment = null;
                getSupportFragmentManager().beginTransaction().replace(R.id.container_content, LoadingFragment.create()).commit();
            } else if (intent.getAction().equals(ModelService.ACTION_RETRY)) {
                converterFragment = null;
                getSupportFragmentManager().beginTransaction().replace(R.id.container_content, LoadFailFragmet.create()).commit();
            } else if (intent.getAction().equals(ModelService.ACTION_CONTENT)) {
                if (converterFragment == null) {
                    converterFragment = ConverterFragment.create(modelService.getContent());
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_content, converterFragment).commit();
                } else {
                    //TODO converterFragment.setContent(...);
                }
            }
        }

    }

}
