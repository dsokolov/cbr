package me.ilich.cbr.model;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class ValCursTest {

    private Data d;

    @Before
    public void setUp() {
        d = new Data();
    }

    @Test
    public void testEquals() {
        assertTrue(d.valCursA1.equals(d.valCursA1));
        assertTrue(d.valCursA1.equals(d.valCursA2));
        assertFalse(d.valCursA1.equals(d.valCursC));
    }

}
