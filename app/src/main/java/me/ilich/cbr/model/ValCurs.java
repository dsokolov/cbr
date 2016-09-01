package me.ilich.cbr.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Root(name = "ValCurs")
public class ValCurs implements Parcelable, Serializable {

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

    protected ValCurs(Parcel in) {
        date = in.readString();
        name = in.readString();
        valute = in.createTypedArrayList(Valute.CREATOR);
    }

    public static final Creator<ValCurs> CREATOR = new Creator<ValCurs>() {
        @Override
        public ValCurs createFromParcel(Parcel in) {
            return new ValCurs(in);
        }

        @Override
        public ValCurs[] newArray(int size) {
            return new ValCurs[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(date);
        parcel.writeString(name);
        parcel.writeTypedList(valute);
    }
}
