package edu.sydney.currency;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

public class DataValidator {
    private final Set<String> allowed;

    public DataValidator(Set<String> allowedCurrencies) {
        this.allowed = allowedCurrencies;
    }

    public boolean isValidCurrency(String currency) {
        if (currency == null) return false;
        String c = currency.trim().toUpperCase(Locale.ROOT);
        return allowed.contains(c);
    }

    public boolean isValidAmount(String amount) {
        return parseAmount(amount).map(a -> a.compareTo(BigDecimal.ZERO) > 0).orElse(false);
    }

    public Optional<BigDecimal> parseAmount(String input) {
        try {
            if (input == null) return Optional.empty();
            String s = input.trim();
            if (s.isEmpty()) return Optional.empty();
            return Optional.of(new BigDecimal(s));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }
}
