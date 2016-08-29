package me.ilich.cbr.model;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CacheTest {

    private Context context;
    private Cache cache;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getContext();
        cache = new Cache();
        cache.clear();
        //TODO clear
    }

    @Test
    public void emptyReplace() {
        try {
            cache.replace(null);
        } catch (Throwable t) {
            assertTrue(t instanceof NullPointerException);
        }
    }

    @Test
    public void get() {
        assertNull(cache.get());
    }

    @Test
    public void replaceGet() {
        ValCurs valCurs = new ValCurs("123", "name", Collections.<Valute>emptyList());
        cache.replace(valCurs);
        ValCurs cached = cache.get();
        assertEquals(valCurs, cached);
        assertTrue(valCurs != cached);
    }

}
