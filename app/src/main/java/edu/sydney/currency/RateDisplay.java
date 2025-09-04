package edu.sydney.currency;

import java.io.PrintStream;
import java.math.BigDecimal;

public class RateDisplay {
    private final CurrencyConverter converter;

    public RateDisplay(CurrencyConverter converter) {
        this.converter = converter;
    }

    public void showAllRatesTable(PrintStream out) {
        String[] ccys = converter.getCurrencies();
        out.println();
        out.println("Exchange Rates (base = 1 unit)");
        StringBuilder header = new StringBuilder(String.format("%-6s", ""));
        for (String c : ccys) header.append(String.format("%-8s", c));
        out.println(header);

        for (String rowCcy : ccys) {
            StringBuilder row = new StringBuilder(String.format("%-6s", rowCcy));
            for (String colCcy : ccys) {
                BigDecimal r = converter.getExchangeRate(rowCcy, colCcy);
                row.append(String.format("%-8s", to2(r)));
            }
            out.println(row);
        }
        out.println();
    }

    public void showRate(PrintStream out, String from, String to) {
        BigDecimal r = converter.getExchangeRate(from, to);
        out.println(from.toUpperCase() + " -> " + to.toUpperCase() + " = " + to2(r));
    }

    public void showAllPairs(PrintStream out) {
        String[] ccys = converter.getCurrencies();
        out.println();
        out.println("All supported pairs and rates:");
        for (String a : ccys) {
            for (String b : ccys) {
                BigDecimal r = converter.getExchangeRate(a, b);
                out.println(a + " -> " + b + " = " + to2(r));
            }
        }
        out.println();
    }

    public void showSameCurrencyRates(PrintStream out) {
        out.println();
        out.println("Same-currency rates:");
        for (String c : converter.getCurrencies()) {
            BigDecimal r = converter.getExchangeRate(c, c);
            out.println(c + " -> " + c + " = " + to2(r));
        }
        out.println();
    }

    private String to2(BigDecimal x) {
        return x.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString();
    }
}
