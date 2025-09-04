package edu.sydney.currency;

public class App {
    public static void main(String[] args) {
        CurrencyConverter converter = new CurrencyConverter();
        DataValidator validator = new DataValidator(converter.getCurrencySet());
        RateDisplay rateDisplay = new RateDisplay(converter);

        UserInterface ui = new UserInterface(
                converter,
                validator,
                rateDisplay,
                System.in,
                System.out
        );

        ui.start();
    }
}
