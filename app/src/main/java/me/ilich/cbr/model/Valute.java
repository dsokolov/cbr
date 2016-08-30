package me.ilich.cbr.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

@Root(name = "Valute")
public class Valute {

    private final String id;
    private final String numCode;
    private final String charCode;
    private final int nominal;
    private final String name;
//    @Convert(Parser.DoubleWithCommaConverter.class)
    private final Double value;

    public Valute(
            @Attribute(name = "ID") String id,
            @Element(name = "NumCode") String numCode,
            @Element(name = "CharCode") String charCode,
            @Element(name = "Nominal") int nominal,
            @Element(name = "Name") String name,
            @Element(name = "Value") /*@Convert(Parser.DoubleWithCommaConverter.class)*/ Double value
    ) {
        this.id = id;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    @Attribute(name = "ID")
    public String getId() {
        return id;
    }

    @Element(name = "NumCode")
    public String getNumCode() {
        return numCode;
    }

    @Element(name = "CharCode")
    public String getCharCode() {
        return charCode;
    }

    @Element(name = "Nominal")
    public int getNominal() {
        return nominal;
    }

    @Element(name = "Name")
    public String getName() {
        return name;
    }

    @Element(name = "Value")
//    @Convert(Parser.DoubleWithCommaConverter.class)
    public Double getValue() {
        return value;
    }

}