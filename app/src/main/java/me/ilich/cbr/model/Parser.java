package me.ilich.cbr.model;

import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;
import org.simpleframework.xml.transform.Transform;

import java.io.Reader;
import java.io.StringReader;

public class Parser {

    private final Persister serializer;

    public Parser() {
        RegistryMatcher m = new RegistryMatcher();
        m.bind(double.class, new DoubleWithCommaTransform());
        m.bind(Double.class, new DoubleWithCommaTransform());
        serializer = new Persister(m);
    }

    public ValCurs parse(String xml) throws ParseException {
        if (xml == null) {
            throw new NullPointerException("xml");
        }
        Reader reader = new StringReader(xml);
        try {
            return serializer.read(ValCurs.class, reader, false);
        } catch (Exception e) {
            throw new ParseException(e);
        }
    }

    public static class ParseException extends Exception {

        public ParseException(Exception e) {
            super(e);
        }

    }

    public static class DoubleWithCommaTransform implements Transform<Double> {

        @Override
        public Double read(String value) throws Exception {
            return Double.parseDouble(value.replace(",", "."));
        }

        @Override
        public String write(Double value) throws Exception {
            return Double.toString(value).replace(".", ",");
        }
    }

}
