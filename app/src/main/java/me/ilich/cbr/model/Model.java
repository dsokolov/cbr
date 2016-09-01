package me.ilich.cbr.model;

import java.util.List;

public interface Model {

    Converter getConverter();

    void loadValutes();

    ValCurs getContent();

}
