package edu.sydney.currency;

import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Scanner;

public class UserInterface {
    private final CurrencyConverter converter;
    private final DataValidator validator;
    private final RateDisplay rateDisplay;
    private final Scanner scanner;
    private final PrintStream out;

    public UserInterface(CurrencyConverter converter,
                         DataValidator validator,
                         RateDisplay rateDisplay,
                         InputStream in,
                         PrintStream out) {
        this.converter = converter;
        this.validator = validator;
        this.rateDisplay = rateDisplay;
        this.scanner = new Scanner(in);
        this.out = out;
    }

    public void start() {
        boolean running = true;
        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    handleConversion();
                    pauseAndReturn();
                    break;
                case "2":
                    handleRatesMenu();
                    pauseAndReturn();
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    out.println("Invalid choice. Try again.");
            }
        }
        out.println("Goodbye.");
    }

    public void showMenu() {
        out.println("=== Currency Converter ===");
        out.println("1. Convert Currency");
        out.println("2. View Exchange Rates");
        out.println("3. Exit");
        out.print("Enter choice: ");
    }

    private void handleRatesMenu() {
        boolean back = false;
        while (!back) {
            out.println();
            out.println("Rates Menu");
            out.println("1. Show rates table");
            out.println("2. Show rate for a specific pair");
            out.println("3. List all supported pairs");
            out.println("4. Show same-currency rates");
            out.println("5. Back");
            out.print("Enter choice: ");
            String c = scanner.nextLine().trim();
            switch (c) {
                case "1":
                    rateDisplay.showAllRatesTable(out);
                    break;
                case "2":
                    String from = promptCurrency("From currency (USD/EUR/GBP/AUD): ");
                    String to = promptCurrency("To currency (USD/EUR/GBP/AUD): ");
                    rateDisplay.showRate(out, from, to);
                    break;
                case "3":
                    rateDisplay.showAllPairs(out);
                    break;
                case "4":
                    rateDisplay.showSameCurrencyRates(out);
                    break;
                case "5":
                    back = true;
                    break;
                default:
                    out.println("Invalid choice. Try again.");
            }
        }
    }

    public void handleConversion() {
        out.println();
        String amountStr = prompt("Enter amount: ");
        while (!validator.isValidAmount(amountStr)) {
            out.println("Invalid amount. Enter a positive number.");
            amountStr = prompt("Enter amount: ");
        }
        BigDecimal amount = validator.parseAmount(amountStr).orElseThrow();

        String from = promptCurrency("From currency (USD/EUR/GBP/AUD): ");
        String to = promptCurrency("To currency (USD/EUR/GBP/AUD): ");

        BigDecimal result = converter.convert(amount, from, to);
        out.println("Result: " +
                converter.formatMoney(amount) + " " + from.toUpperCase(Locale.ROOT) +
                " = " + converter.formatMoney(result) + " " + to.toUpperCase(Locale.ROOT));
    }

    private String prompt(String message) {
        out.print(message);
        return scanner.nextLine().trim();
    }

    private String promptCurrency(String message) {
        String ccy = prompt(message).toUpperCase(Locale.ROOT);
        while (!validator.isValidCurrency(ccy)) {
            out.println("Invalid currency code. Allowed: USD, EUR, GBP, AUD.");
            ccy = prompt(message).toUpperCase(Locale.ROOT);
        }
        return ccy;
    }

    private void pauseAndReturn() {
        out.println();
        out.println("Returning to main menu...");
        out.println();
    }
}
