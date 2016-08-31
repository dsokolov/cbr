package me.ilich.cbr.model;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

public class ModelService extends Service {

    private static final Valute RUR = new Valute("", "", "RUR", 1, "Рубль", 1.0);
    public static final String ACTION_LOADING = "loading";
    public static final String ACTION_CONTENT = "content";
    public static final String ACTION_RETRY = "retry";

    public static Intent intent(Context context) {
        return new Intent(context, ModelService.class);
    }

    public static IntentFilter intentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ModelService.ACTION_LOADING);
        intentFilter.addAction(ModelService.ACTION_CONTENT);
        intentFilter.addAction(ModelService.ACTION_RETRY);
        return intentFilter;
    }

    private final IBinder binder = new LocalBinder();
    private final Cache cache = new Cache();
    private final Converter converter = new Converter();
    private final Parser parser = new Parser();
    private final ServiceIntegration serviceIntegration = new ServiceIntegration();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void loadValutes() {
        if (cache.contains()) {
            sendContent();
        } else {
            sendLoading();
        }

        AsyncTask<Void, Void, ValCurs> task = new AsyncTask<Void, Void, ValCurs>() {
            @Override
            protected ValCurs doInBackground(Void... voids) {
                ValCurs result = null;
                try {
                    String s = serviceIntegration.daily();
                    result = parser.parse(s);
                    result.getValute().add(1, RUR);
                    cache.replace(result);
                } catch (ServiceIntegration.IntegrationException | Parser.ParseException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(ValCurs valCurs) {
                super.onPostExecute(valCurs);
                if (valCurs == null) {
                    if (cache.contains()) {
                        sendContent();
                    } else {
                        sendRetry();
                    }
                } else {
                    sendContent();
                }
            }
        };
        task.execute();
    }

    public ValCurs getContent() {
        return cache.get();
    }

    private void sendRetry() {
        LocalBroadcastManager.getInstance(ModelService.this).sendBroadcast(new Intent(ACTION_RETRY));
    }

    private void sendLoading() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_LOADING));
    }

    private void sendContent() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_CONTENT));
    }

    public Converter getConverter() {
        return converter;
    }

    public class LocalBinder extends Binder {

        public ModelService getService() {
            return ModelService.this;
        }

    }

}
