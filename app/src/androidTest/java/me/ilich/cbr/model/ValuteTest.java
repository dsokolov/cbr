package me.ilich.cbr.model;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ValuteTest {

    private Data d;

    @Before
    public void setUp() {
        d = new Data();
    }

    @Test
    public void equalsTest() {
        assertTrue(d.valuteA1.equals(d.valuteA1));
        assertTrue(d.valuteA1.equals(d.valuteA2));
        assertFalse(d.valuteA1.equals(d.valuteC));

    }

}
