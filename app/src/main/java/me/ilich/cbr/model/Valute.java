package me.ilich.cbr.model;

public class Valute {

    private final String id;
    private final String numCode;
    private final String aud;
    private final int nominal;
    private final String name;
    private final double value;

    public Valute(String id, String numCode, String aud, int nominal, String name, double value) {
        this.id = id;
        this.numCode = numCode;
        this.aud = aud;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    public String getAud() {
        return aud;
    }

    public int getNominal() {
        return nominal;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

}