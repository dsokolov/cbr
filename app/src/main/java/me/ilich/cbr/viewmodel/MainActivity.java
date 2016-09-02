package me.ilich.cbr.viewmodel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;

import me.ilich.cbr.R;
import me.ilich.cbr.model.ModelService;

public class MainActivity extends ViewModelActivity implements LoadFailFragmet.Callback {

    private static final String TAG_FRAGMENT_CONVERTER = "fragment converter";

    private LocalBroadcastReceiver broadcastReceiver = new LocalBroadcastReceiver();

    private ConverterFragment converterFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, ModelService.intentFilter());
        if (savedInstanceState != null) {
            Fragment contentFragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_CONVERTER);
            if (contentFragment instanceof ConverterFragment) {
                converterFragment = (ConverterFragment) contentFragment;
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Fragment contentFragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_CONVERTER);
        if (contentFragment instanceof ConverterFragment) {
            converterFragment = (ConverterFragment) contentFragment;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        converterFragment = null;
    }

    @Override
    public void onBound() {
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
            if (intent.getAction().equals(ModelService.ACTION_LOADING)) {
                converterFragment = null;
                getSupportFragmentManager().beginTransaction().replace(R.id.container_content, LoadingFragment.create()).commit();
            } else if (intent.getAction().equals(ModelService.ACTION_RETRY)) {
                converterFragment = null;
                getSupportFragmentManager().beginTransaction().replace(R.id.container_content, LoadFailFragmet.create()).commit();
            } else if (intent.getAction().equals(ModelService.ACTION_CONTENT)) {
                if (converterFragment == null) {
                    converterFragment = ConverterFragment.create();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_content, converterFragment, TAG_FRAGMENT_CONVERTER).commit();
                }
            }
        }

    }

}
