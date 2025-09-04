package edu.sydney.currency;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class RateDisplayTest {
    @Test
    void testShowAllRatesTablePrintsMatrix() {
        CurrencyConverter c = new CurrencyConverter();
        RateDisplay rd = new RateDisplay(c);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        rd.showAllRatesTable(new PrintStream(baos));
        String out = baos.toString();
        assertTrue(out.contains("Exchange Rates"));
        assertTrue(out.contains("USD"));
        assertTrue(out.contains("EUR"));
        assertTrue(out.contains("GBP"));
        assertTrue(out.contains("AUD"));
        assertTrue(out.contains("0.85")); 
        assertTrue(out.contains("1.18")); 
    }

    @Test
    void testShowRateSpecificPair() {
        CurrencyConverter c = new CurrencyConverter();
        RateDisplay rd = new RateDisplay(c);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        rd.showRate(new PrintStream(baos), "USD", "GBP");
        assertTrue(baos.toString().contains("USD -> GBP = 0.75"));
    }
}
