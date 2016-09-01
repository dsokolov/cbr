package me.ilich.cbr.model;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CacheTest {

    private Context context;
    private Cache cache;

    @Before
    public void setUp() {
        //context = InstrumentationRegistry.getInstrumentation().getContext();
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        cache = new Cache(context);
        cache.clear();
    }

    @Test
    public void emptyReplace() {
        try {
            cache.replaceValCurs(null);
        } catch (Throwable t) {
            assertTrue(t instanceof NullPointerException);
        }
    }

    @Test
    public void get() {
        assertNull(cache.getValCurs());
    }

    @Test
    public void replaceGet() {
        ValCurs valCurs = new ValCurs("123", "name", Collections.<Valute>emptyList());
        cache.replaceValCurs(valCurs);
        ValCurs cached = cache.getValCurs();
        assertEquals(valCurs, cached);
    }

    @Test
    public void replaceGetNewInstance() {
        ValCurs valCurs = new ValCurs("123", "name", Collections.<Valute>emptyList());
        cache.replaceValCurs(valCurs);
        Cache cache1 = new Cache(context);
        ValCurs cached = cache1.getValCurs();
        assertEquals(valCurs, cached);
        assertFalse(valCurs == cached);
    }

}
