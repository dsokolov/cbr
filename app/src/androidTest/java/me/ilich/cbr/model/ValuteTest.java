package me.ilich.cbr.model;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ValuteTest {

    @Test
    public void equalsTest() {
        Valute a = new Valute("idA", "codeA", "audA", 1, "A", 1.0);
        Valute b = new Valute("idA", "codeA", "audA", 1, "A", 1.0);
        Valute c = new Valute("idC", "codeC", "audC", 3, "C", 2.0);
        assertTrue(a.equals(a));
        assertTrue(a.equals(b));
        assertFalse(a.equals(c));

    }

}
