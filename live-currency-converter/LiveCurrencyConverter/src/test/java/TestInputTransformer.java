import io.testproject.addon.currency.converter.common.CurrencyExchangeWrapper;
import io.testproject.addon.currency.converter.common.InputTransformer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestInputTransformer {

    @Test
    public void testExtractCurrencyUSD() {
        assertEquals(InputTransformer.extractCurrencyStr("100$"), "USD");
    }

    @Test
    public void testExtractCurrencyEUR() {
        assertEquals(InputTransformer.extractCurrencyStr("100€"), "EUR");
    }

    @Test
    public void testExtractCurrencyILS() {
        assertEquals(InputTransformer.extractCurrencyStr("100₪"), "ILS");
    }

    @Test
    public void testExtractCurrencyJPY() {
        assertEquals(InputTransformer.extractCurrencyStr("100¥"), "JPY");
    }

}
