package me.ilich.cbr.model;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private static Model model = new Model();

    public static Model getInstance() {
        return model;
    }

    private final Cache cache = new Cache();
    private final Converter converter = new Converter();

    public List<Valute> getValutes() {
        List<Valute> valutes = new ArrayList<>();
        valutes.add(new Valute("1", "nc", "cc", 1, "name", 123.0));
        valutes.add(new Valute("2", "nc2", "cc2", 1, "name2", 323.0));
        return valutes;
    }

    public Converter getConverter() {
        return converter;
    }

}
