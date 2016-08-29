package me.ilich.cbr.model;

import java.util.ArrayList;
import java.util.List;

public class ValCurs {

    private final String date;
    private final String name;
    private final List<Valute> valutes;

    public ValCurs(String date, String name, List<Valute> valutes) {
        this.date = date;
        this.name = name;
        this.valutes = new ArrayList<>(valutes);
    }

}
