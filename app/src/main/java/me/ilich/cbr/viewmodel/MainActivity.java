package me.ilich.cbr.viewmodel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import me.ilich.cbr.R;
import me.ilich.cbr.model.ModelService;

public class MainActivity extends ViewModelActivity implements LoadFailFragmet.Callback {

    private LocalBroadcastReceiver broadcastReceiver = new LocalBroadcastReceiver();

    private ConverterFragment converterFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("Sokolov", "1");
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, ModelService.intentFilter());
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
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onBound() {
        Log.v("Sokolov", "2");
        if (isFirstStart()) {
            getModel().loadValutes();
        }
    }

    @Override
    public void onRetryLoading() {
        getModel().loadValutes();
    }

    private class LocalBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("Sokolov", intent.getAction());
            if (intent.getAction().equals(ModelService.ACTION_LOADING)) {
                converterFragment = null;
                getSupportFragmentManager().beginTransaction().replace(R.id.container_content, LoadingFragment.create()).commit();
            } else if (intent.getAction().equals(ModelService.ACTION_RETRY)) {
                converterFragment = null;
                getSupportFragmentManager().beginTransaction().replace(R.id.container_content, LoadFailFragmet.create()).commit();
            } else if (intent.getAction().equals(ModelService.ACTION_CONTENT)) {
                if (converterFragment == null) {
                    converterFragment = ConverterFragment.create();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_content, converterFragment).commit();
                }
            }
        }

    }

}
