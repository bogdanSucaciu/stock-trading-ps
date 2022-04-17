package com.pluralsight.springwebflux5.stockmarket.controller;


import com.pluralsight.springwebflux5.stockmarket.api.CurrencyRate;
import com.pluralsight.springwebflux5.stockmarket.api.StockPublishRequest;
import com.pluralsight.springwebflux5.stockmarket.api.StockPublishResponse;
import com.pluralsight.springwebflux5.stockmarket.service.CurrencyRatesService;
import com.pluralsight.springwebflux5.stockmarket.service.StockPublishingService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class StockMarketController {

    private CurrencyRatesService currencyRatesService;
    private StockPublishingService stockPublishingService;

    @GetMapping("/currencyRates")
    public Flux<CurrencyRate> getCurrencyRates() {
        return currencyRatesService.getCurrencyRates();
    }

    @PostMapping("/stocks/publish")
    public Mono<StockPublishResponse> publishStock(@RequestBody StockPublishRequest request) {
        return stockPublishingService.publishStock(request);
    }
}
