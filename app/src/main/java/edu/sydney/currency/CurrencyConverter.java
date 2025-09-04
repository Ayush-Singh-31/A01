package edu.sydney.currency;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class CurrencyConverter {
    public static final String USD = "USD";
    public static final String EUR = "EUR";
    public static final String GBP = "GBP";
    public static final String AUD = "AUD";

    private static final MathContext MC = new MathContext(20, RoundingMode.HALF_UP);
    private static final int SCALE = 2;

    private final Map<String, BigDecimal> fromUSD;

    public CurrencyConverter() {
        this.fromUSD = new LinkedHashMap<>();
        fromUSD.put(USD, bd("1.00"));
        fromUSD.put(EUR, bd("0.85"));
        fromUSD.put(GBP, bd("0.75"));
        fromUSD.put(AUD, bd("1.30"));
    }

    public String[] getCurrencies() {
        return fromUSD.keySet().toArray(new String[0]);
    }

    public Set<String> getCurrencySet() {
        return fromUSD.keySet();
    }

    public BigDecimal getExchangeRate(String from, String to) {
        String f = norm(from);
        String t = norm(to);
        if (!fromUSD.containsKey(f) || !fromUSD.containsKey(t)) {
            throw new IllegalArgumentException("Unsupported currency");
        }
        return fromUSD.get(t).divide(fromUSD.get(f), MC);
    }

    public BigDecimal convert(BigDecimal amount, String from, String to) {
        if (amount == null) throw new IllegalArgumentException("Amount is null");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        return amount.multiply(getExchangeRate(from, to), MC);
    }

    public String formatMoney(BigDecimal amount) {
        return amount.setScale(SCALE, RoundingMode.HALF_UP).toPlainString();
    }

    private static String norm(String ccy) {
        return ccy == null ? "" : ccy.trim().toUpperCase(Locale.ROOT);
    }

    private static BigDecimal bd(String s) {
        return new BigDecimal(s, MC);
    }
}
