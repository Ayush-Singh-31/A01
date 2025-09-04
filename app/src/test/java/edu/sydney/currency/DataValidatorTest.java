package edu.sydney.currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class DataValidatorTest {
    DataValidator v;

    @BeforeEach
    void setUp() {
        v = new DataValidator(Set.of("USD","EUR","GBP","AUD"));
    }

    @Test
    void testValidCurrenciesCaseInsensitive() {
        assertTrue(v.isValidCurrency("usd"));
        assertTrue(v.isValidCurrency("EUR"));
        assertTrue(v.isValidCurrency("GbP"));
        assertTrue(v.isValidCurrency("aud"));
    }

    @Test
    void testInvalidCurrency() {
        assertFalse(v.isValidCurrency("INR"));
        assertFalse(v.isValidCurrency(""));
        assertFalse(v.isValidCurrency(null));
    }

    @Test
    void testValidAmount() {
        assertTrue(v.isValidAmount("10"));
        assertTrue(v.isValidAmount("10.50"));
    }

    @Test
    void testInvalidAmount() {
        assertFalse(v.isValidAmount("-1"));
        assertFalse(v.isValidAmount("abc"));
        assertFalse(v.isValidAmount(""));
        assertFalse(v.isValidAmount(null));
    }
}
