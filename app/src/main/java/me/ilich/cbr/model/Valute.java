package me.ilich.cbr.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Valute")
public class Valute implements Parcelable {

    private final String id;
    private final String numCode;
    private final String charCode;
    private final int nominal;
    private final String name;
    private final Double value;

    public Valute(
            @Attribute(name = "ID") String id,
            @Element(name = "NumCode") String numCode,
            @Element(name = "CharCode") String charCode,
            @Element(name = "Nominal") int nominal,
            @Element(name = "Name") String name,
            @Element(name = "Value") Double value
    ) {
        this.id = id;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    protected Valute(Parcel in) {
        id = in.readString();
        numCode = in.readString();
        charCode = in.readString();
        nominal = in.readInt();
        name = in.readString();
        value = in.readDouble();
    }

    public static final Creator<Valute> CREATOR = new Creator<Valute>() {
        @Override
        public Valute createFromParcel(Parcel in) {
            return new Valute(in);
        }

        @Override
        public Valute[] newArray(int size) {
            return new Valute[size];
        }
    };

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Valute valute = (Valute) o;

        if (nominal != valute.nominal) return false;
        if (id != null ? !id.equals(valute.id) : valute.id != null) return false;
        if (numCode != null ? !numCode.equals(valute.numCode) : valute.numCode != null)
            return false;
        if (charCode != null ? !charCode.equals(valute.charCode) : valute.charCode != null)
            return false;
        if (name != null ? !name.equals(valute.name) : valute.name != null) return false;
        return value != null ? value.equals(valute.value) : valute.value == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (numCode != null ? numCode.hashCode() : 0);
        result = 31 * result + (charCode != null ? charCode.hashCode() : 0);
        result = 31 * result + nominal;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(numCode);
        parcel.writeString(charCode);
        parcel.writeInt(nominal);
        parcel.writeString(name);
        parcel.writeDouble(value);
    }

}