package edu.sydney.currency;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.math.RoundingMode;
import static org.junit.jupiter.api.Assertions.*;

class CurrencyConverterTest {
    @Test
    void testGetExchangeRateSameCurrencyIsOne() {
        CurrencyConverter c = new CurrencyConverter();
        assertEquals("1.00",
            c.getExchangeRate(CurrencyConverter.EUR, CurrencyConverter.EUR)
             .setScale(2, RoundingMode.HALF_UP).toPlainString());
    }

    @Test
    void testUsdToEurMatchesBase() {
        CurrencyConverter c = new CurrencyConverter();
        String r = c.getExchangeRate(CurrencyConverter.USD, CurrencyConverter.EUR)
                .setScale(2, RoundingMode.HALF_UP).toPlainString();
        assertEquals("0.85", r);
    }

    @Test
    void testCrossRateEurToGbp() {
        CurrencyConverter c = new CurrencyConverter();
        BigDecimal r = c.getExchangeRate(CurrencyConverter.EUR, CurrencyConverter.GBP);
        assertEquals("0.88", r.setScale(2, RoundingMode.HALF_UP).toPlainString());
    }

    @Test
    void testConvertAmount() {
        CurrencyConverter c = new CurrencyConverter();
        BigDecimal res = c.convert(new BigDecimal("100"), CurrencyConverter.USD, CurrencyConverter.EUR);
        assertEquals("85.00", c.formatMoney(res));
    }

    @Test
    void testInvalidCurrencyThrows() {
        CurrencyConverter c = new CurrencyConverter();
        assertThrows(IllegalArgumentException.class, () ->
                c.getExchangeRate("XYZ", CurrencyConverter.USD));
    }

    @Test
    void testNegativeAmountThrows() {
        CurrencyConverter c = new CurrencyConverter();
        assertThrows(IllegalArgumentException.class, () ->
                c.convert(new BigDecimal("-5"), CurrencyConverter.USD, CurrencyConverter.EUR));
    }
}
