package me.ilich.cbr.view;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import me.ilich.cbr.R;
import me.ilich.cbr.model.ModelService;

public class MainActivity extends AppCompatActivity {

    private final ModelServiceConnection serviceConnection = new ModelServiceConnection();
    private ModelService modelService;
    private boolean bound = false;
    private LocalBroadcastReceiver broadcastReceiver = new LocalBroadcastReceiver();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ModelService.ACTION_NO_CACHE);
        intentFilter.addAction(ModelService.ACTION_HAS_CACHE);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
        bindService(ModelService.intent(this), serviceConnection, BIND_AUTO_CREATE);
        if (savedInstanceState == null) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.container_content, LoadingFragment.create()).commit();
            //getSupportFragmentManager().beginTransaction().replace(R.id.container_content, ConverterFragment.create()).commit();
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
        modelService.loadValutes();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_content, ConverterFragment.create()).commit();
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
            if (intent.getAction().equals(ModelService.ACTION_NO_CACHE)) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_content, LoadingFragment.create()).commit();
            }
        }

    }

}
