package com.healthcare.billing.model;

public class Currency {
    private String identifier;
    private char symbol;

    public Currency() {

    }

    public Currency(String identifier, char symbol) {
        this.identifier = identifier;
        this.symbol = symbol;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "identifier='" + identifier + '\'' +
                ", symbol=" + symbol +
                '}';
    }
}
