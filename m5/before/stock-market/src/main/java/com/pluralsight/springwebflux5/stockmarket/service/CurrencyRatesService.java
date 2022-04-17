package com.pluralsight.springwebflux5.stockmarket.service;

import com.pluralsight.springwebflux5.stockmarket.api.currencyrate.CurrencyRate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CurrencyRatesService {

    private static final List<CurrencyRate> rates = List.of(
            CurrencyRate.builder().currencyName("USD").rate(BigDecimal.ONE).build(),
            CurrencyRate.builder().currencyName("EUR").rate(BigDecimal.valueOf(1.15)).build(),
            CurrencyRate.builder().currencyName("CAD").rate(BigDecimal.valueOf(0.8)).build(),
            CurrencyRate.builder().currencyName("AUD").rate(BigDecimal.valueOf(0.75)).build()
    );

    public Flux<CurrencyRate> getCurrencyRates() {
        return Flux.fromIterable(rates);
    }
}
