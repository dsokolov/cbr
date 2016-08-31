package me.ilich.cbr.model;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private static Model model = new Model();

    public static Model getInstance() {
        return model;
    }

    private final Converter converter = new Converter();

    public Converter getConverter() {
        return converter;
    }

    public List<Valute> getValutes() {
        List<Valute> valutes = new ArrayList<>();
        valutes.add(new Valute("1", "nc", "cc", 1, "name", 123.0));
        valutes.add(new Valute("2", "nc2", "cc2", 1, "name2", 323.0));
        return valutes;
    }

    public interface OnLoadValutesListener {

        void onLoaded(ValCurs valutes);

        void onFail();

    }

}
