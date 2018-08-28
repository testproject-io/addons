import io.testproject.addon.currency.converter.common.CurrencyExchangeWrapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestCurrencyExchangeWrapper {

    private CurrencyExchangeWrapper wrapper;

    @Before
    public void init() {
        wrapper = new CurrencyExchangeWrapper("USD", "EUR");
        CurrencyExchangeWrapper.updateAllSupportedCurrencies();
    }

    @Test
    public void testConversionRate() {
        assertNotNull(wrapper.getConversionRate());
    }

    @Test
    public void testGetSupportedCurrenciesString() {
        assertNotNull(CurrencyExchangeWrapper.getAllSupportedCurrenciesAsString());
    }

    @Test
    public void testGetSupportedCurrencies() {
        assertNotNull(CurrencyExchangeWrapper.allSupportedCurrencies);
    }

    @After
    public void finish() {

    }

}
