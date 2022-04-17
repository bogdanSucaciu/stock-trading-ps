package com.pluralsight.springwebflux5.stockmarket.exception;

import com.pluralsight.springwebflux5.stockmarket.api.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StockExceptionHandler {

    @ExceptionHandler(StockPublishingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleStockPublishingException(StockPublishingException ex) {
        return ErrorMessage.builder()
                .message(ex.getMessage())
                .build();
    }
}
