package org.example;

import java.io.IOException;

public class Main {
    //private to ensure that its value is not accidentally changed by some other part of  code.
    // The path to the JSON file
    private static final String JSON_FILE_PATH = "quotes.json";

    public static void main(String[] args) throws IOException {
        QuoteOfTheDay quoteOfTheDay = new QuoteOfTheDay(JSON_FILE_PATH);
        quoteOfTheDay.printQuoteOfTheDay();
    }
}