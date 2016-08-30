package me.ilich.cbr.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "ValCurs")
public class ValCurs {

    private final String date;
    private final String name;
    private final List<Valute> valute;

    public ValCurs(
            @Attribute(name = "Date") String date,
            @Attribute(name = "name") String name,
            @ElementList(inline = true, name = "Valute") List<Valute> valute
    ) {
        this.date = date;
        this.name = name;
        this.valute = valute;
    }

    @Attribute(name = "name")
    public String getName() {
        return name;
    }

    @Attribute(name = "Date")
    public String getDate() {
        return date;
    }

    @ElementList(inline = true, name = "Valute")
    public List<Valute> getValute() {
        return valute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValCurs valCurs = (ValCurs) o;

        if (date != null ? !date.equals(valCurs.date) : valCurs.date != null) return false;
        if (name != null ? !name.equals(valCurs.name) : valCurs.name != null) return false;
        return valute != null ? valute.equals(valCurs.valute) : valCurs.valute == null;

    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (valute != null ? valute.hashCode() : 0);
        return result;
    }

}
