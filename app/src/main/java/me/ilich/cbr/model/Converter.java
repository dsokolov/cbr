package me.ilich.cbr.model;

public class Converter {

    public double convert(Valute source, Valute target, double amount) {
        checkValute("source", source);
        checkValute("target", target);
        if (amount < 0) {
            throw new IllegalArgumentException("amount");
        }
        double sourcePerPiece = source.getValue() / source.getNominal();
        double targetPerPiece = target.getValue() / target.getNominal();
        double rate = sourcePerPiece / targetPerPiece;
        return rate * amount;
    }

    private void checkValute(String s, Valute valute) {
        if (valute.getNominal() <= 0) {
            throw new IllegalArgumentException(s + " nominal");
        }
        if (valute.getValue() <= 0) {
            throw new IllegalArgumentException(s + " value");
        }
    }

}
