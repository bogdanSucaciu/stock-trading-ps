package com.pluralsight.springwebflux5.stockmarket.exception;

public class StockPublishingException extends RuntimeException {

    public StockPublishingException(String message) {
        super(message);
    }
}
