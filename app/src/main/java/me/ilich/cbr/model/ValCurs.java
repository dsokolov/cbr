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

}
