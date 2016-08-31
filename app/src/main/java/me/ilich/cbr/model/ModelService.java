package me.ilich.cbr.model;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

public class ModelService extends Service {

    public static final String ACTION_NO_CACHE = "no cache";
    public static final String ACTION_HAS_CACHE = "has cache";

    public static Intent intent(Context context) {
        return new Intent(context, ModelService.class);
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
        Intent intent = new Intent();
        intent.setAction(ACTION_NO_CACHE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

        AsyncTask<Void, Void, ValCurs> task = new AsyncTask<Void, Void, ValCurs>() {
            @Override
            protected ValCurs doInBackground(Void... voids) {
                ValCurs result = null;
                try {
                    String s = serviceIntegration.daily();
                    result = parser.parse(s);
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
                        //listener.onLoaded(cache.get());
                    } else {
                        //listener.onFail();
                    }
                } else {
                    //listener.onLoaded(valCurs);
                }
            }
        };
        //task.execute();
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
