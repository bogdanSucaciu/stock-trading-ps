package com.pluralsight.springwebflux5.stockmarket.service;

import com.pluralsight.springwebflux5.stockmarket.api.stockpublish.StockPublishRequest;
import com.pluralsight.springwebflux5.stockmarket.api.stockpublish.StockPublishResponse;
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
                .price(request.getPrice())
                .stockName(request.getStockName())
                .currencyName(request.getCurrencyName())
                .status(getStatus(request))
                .build();
    }

    private String getStatus(StockPublishRequest request) {
        if(request.getStockName().contains("-"))
            throw new StockPublishingException(
                "Stock name contains illegal character '-'");
        return  "SUCCESS";
    }
}
