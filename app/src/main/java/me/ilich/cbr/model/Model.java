package me.ilich.cbr.model;

public interface Model {

    Converter getConverter();

    void loadValutes();

    ValCurs getContent();

}
