package me.ilich.cbr.model;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.ilich.cbr.AssertsTools;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class ParserTest {

    private Context context;
    private Parser parser;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getContext();
        parser = new Parser();
    }

    @Test
    public void parseEmpty() {
        try {
            parser.parse(null);
            fail();
        } catch (Throwable t) {
            assertTrue(t instanceof NullPointerException);
        }
    }

    @Test
    public void parseSuccess() {
        try {
            ValCurs valCurs = parser.parse(AssertsTools.asString(context, "valcurs/valcurs_success.xml"));
            assertNotNull(valCurs);
        } catch (Parser.ParseException e) {
            fail(e.getMessage());
        }
    }

}
