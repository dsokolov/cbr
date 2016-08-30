package me.ilich.cbr.model;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ServiceIntegrationTest {

    private ServiceIntegration serviceIntegration;

    @Before
    public void setUp() {
        serviceIntegration = new ServiceIntegration();
    }

    @Test
    public void request() {
        try {
            String s = serviceIntegration.daily();
            assertNotNull(s);
        } catch (ServiceIntegration.IntegrationException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void requestAndParse() {
        try {
            String s = serviceIntegration.daily();
            Parser parser = new Parser();
            ValCurs curs = parser.parse(s);
            assertNotNull(curs);
            assertFalse(TextUtils.isEmpty(curs.getName()));
            assertFalse(TextUtils.isEmpty(curs.getDate()));
            assertNotNull(curs.getValute());
            assertTrue(curs.getValute().size() > 0);
        } catch (ServiceIntegration.IntegrationException | Parser.ParseException e) {
            fail(e.getMessage());
        }
    }

}
