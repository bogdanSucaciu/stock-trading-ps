package com.pluralsight.springwebflux5.stocktrading.exception;

public class StockCreationException extends RuntimeException {

    public StockCreationException(String message) {
        super(message);
    }
}
