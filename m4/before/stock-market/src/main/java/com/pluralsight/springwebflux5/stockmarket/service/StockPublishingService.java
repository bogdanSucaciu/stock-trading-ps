package com.pluralsight.springwebflux5.stockmarket.service;

import com.pluralsight.springwebflux5.stockmarket.api.StockPublishRequest;
import com.pluralsight.springwebflux5.stockmarket.api.StockPublishResponse;
import com.pluralsight.springwebflux5.stockmarket.exception.StockPublishingException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StockPublishingService {

    public Mono<StockPublishResponse> publishStock(StockPublishRequest request) {
        return Mono.just(request)
            .map(this::persistStock);
    }

    private StockPublishResponse persistStock(StockPublishRequest request) {
        return StockPublishResponse.builder()
                .amount(request.getAmount())
                .stockName(request.getStockName())
                .currencyName(request.getCurrencyName())
                .status(getStatus(request))
                .build();
    }

    private String getStatus(StockPublishRequest request) {
        if(request.getStockName().contains("-"))
            throw new StockPublishingException("Stock name contains illegal character '-': ");
        return  "SUCCESS";
    }
}
