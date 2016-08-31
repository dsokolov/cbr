package me.ilich.cbr.model;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private static Model model = new Model();

    public static Model getInstance() {
        return model;
    }

    private final Cache cache = new Cache();
    private final Converter converter = new Converter();
    private final Parser parser = new Parser();
    private final ServiceIntegration serviceIntegration = new ServiceIntegration();

    public List<Valute> getValutes() {
        List<Valute> valutes = new ArrayList<>();
        valutes.add(new Valute("1", "nc", "cc", 1, "name", 123.0));
        valutes.add(new Valute("2", "nc2", "cc2", 1, "name2", 323.0));
        return valutes;
    }

    public Converter getConverter() {
        return converter;
    }

    public void loadValutes(final OnLoadValutesListener listener) {
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
                        listener.onLoaded(cache.get());
                    } else {
                        listener.onFail();
                    }
                } else {
                    listener.onLoaded(valCurs);
                }
            }
        };
        task.execute();
    }

    public interface OnLoadValutesListener {

        void onLoaded(ValCurs valutes);

        void onFail();

    }

}
