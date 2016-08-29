package me.ilich.cbr.model;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ConverterTest {

    private Data d;
    private Converter converter;

    @Before
    public void setUp() {
        d = new Data();
        converter = new Converter();
    }

    @Test
    public void empty() {
        try {
            converter.convert(null, d.valuteC, 0);
        } catch (Throwable t) {
            assertTrue(t instanceof NullPointerException);
        }
        try {
            converter.convert(d.valuteB, null, 0);
        } catch (Throwable t) {
            assertTrue(t instanceof NullPointerException);
        }
    }

    @Test
    public void invalid() {
        try {
            converter.convert(d.valuteB, d.valuteC, -100.0);
        } catch (Throwable t) {
            assertTrue(t instanceof IllegalArgumentException);
        }
        try {
            converter.convert(d.valuteInvalidNominal, d.valuteC, 100.0);
        } catch (Throwable t) {
            assertTrue(t instanceof IllegalArgumentException);
        }
        try {
            converter.convert(d.valuteInvalidValue, d.valuteC, 100.0);
        } catch (Throwable t) {
            assertTrue(t instanceof IllegalArgumentException);
        }
        try {
            converter.convert(d.valuteB, d.valuteInvalidNominal, 100.0);
        } catch (Throwable t) {
            assertTrue(t instanceof IllegalArgumentException);
        }
        try {
            converter.convert(d.valuteB, d.valuteInvalidValue, 100.0);
        } catch (Throwable t) {
            assertTrue(t instanceof IllegalArgumentException);
        }
    }

    @Test
    public void convert() {
        final double delta = 0.01;

        assertEquals(0.0, converter.convert(d.valuteB, d.valuteC, 0.0), delta);

        assertEquals(1.0, converter.convert(new Valute("", "", "", 1, "", 1.0), new Valute("", "", "", 1, "", 1.0), 1.0), delta);

        assertEquals(1.54, converter.convert(d.rur, d.usd, 100.0), delta);
        assertEquals(6473.8, converter.convert(d.usd, d.rur, 100.0), delta);

        assertEquals(0.39, converter.convert(d.uah, d.usd, 10.0), delta);
        assertEquals(254.5, converter.convert(d.usd, d.uah, 10.0), delta);
    }

}
