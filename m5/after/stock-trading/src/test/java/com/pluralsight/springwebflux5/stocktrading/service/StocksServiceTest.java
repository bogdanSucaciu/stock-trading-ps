package com.pluralsight.springwebflux5.stocktrading.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import com.pluralsight.springwebflux5.stockmarket.api.stockpublish.StockPublishResponse;
import com.pluralsight.springwebflux5.stocktrading.client.StockMarketClient;
import com.pluralsight.springwebflux5.stocktrading.dto.StockRequest;
import com.pluralsight.springwebflux5.stocktrading.exception.StockCreationException;
import com.pluralsight.springwebflux5.stocktrading.model.Stock;
import com.pluralsight.springwebflux5.stocktrading.repository.StocksRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class StocksServiceTest {
    
    private static final String STOCK_ID = "621a97f1d11fc40fcdd5c67b";
    private static final String STOCK_NAME = "Globomantics";
    private static final BigDecimal STOCK_PRICE = BigDecimal.TEN;
    private static final String STOCK_CURRENCY = "USD";

    @Mock
    private StocksRepository stocksRepository;

    @Mock
    private StockMarketClient stockMarketClient;

    @InjectMocks
    private StocksService stocksService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateStock() {
        // GIVEN
        StockRequest stockRequest = StockRequest.builder()
                                            .name(STOCK_NAME)
                                            .price(STOCK_PRICE)
                                            .currency(STOCK_CURRENCY)
                                            .build();
        Stock stock = Stock.builder()
                        .id(STOCK_ID)
                        .name(STOCK_NAME)
                        .price(STOCK_PRICE)
                        .currency(STOCK_CURRENCY)
                        .build();
        StockPublishResponse stockPublishResponse = 
                                    StockPublishResponse.builder()
                                        .stockName(STOCK_NAME)
                                        .price(STOCK_PRICE)
                                        .currencyName(STOCK_CURRENCY)
                                        .status("SUCCESS")
                                        .build();
        when(stocksRepository.save(any()))
                        .thenReturn(Mono.just(stock));
        when(stockMarketClient.publishStock(any()))
                .thenReturn(Mono.just(stockPublishResponse));
        // WHEN
        StepVerifier.create(stocksService.createStock(stockRequest))
        //THEN
            .assertNext(stockResponse -> {
                assertNotNull(stockResponse);
                assertEquals(STOCK_ID, stockResponse.getId());
                assertEquals(STOCK_NAME, stockResponse.getName());
                assertEquals(STOCK_PRICE, stockResponse.getPrice());
                assertEquals(STOCK_CURRENCY, stockResponse.getCurrency());
            })
            .verifyComplete();    
    }

    @Test
    void shouldThrowStockCreationExceptionWhenUnableToSave() {
        // GIVEN
        StockRequest stockRequest = StockRequest.builder()
                                                .name(STOCK_NAME)
                                                .price(STOCK_PRICE)
                                                .currency(STOCK_CURRENCY)
                                                .build();
        when(stocksRepository.save(any()))
                .thenThrow(new RuntimeException("Connection Lost"));
        // WHEN
        StepVerifier.create(stocksService.createStock(stockRequest))
        // THEN
                    .verifyError(StockCreationException.class);
    }

    @Test
    void shouldThrowStockCreationExceptionWhenStockMarketFailed() {
        // GIVEN
        StockRequest stockRequest = StockRequest.builder()
                                                .name(STOCK_NAME)
                                                .price(STOCK_PRICE)
                                                .currency(STOCK_CURRENCY)
                                                .build();
        Stock stock = Stock.builder()
                           .id(STOCK_ID)
                           .name(STOCK_NAME)
                           .price(STOCK_PRICE)
                           .currency(STOCK_CURRENCY)
                           .build();
        StockPublishResponse stockPublishResponse = 
                                            StockPublishResponse.builder()
                                                .stockName(STOCK_NAME)
                                                .price(STOCK_PRICE)
                                                .currencyName(STOCK_CURRENCY)
                                                .status("FAIL")
                                                .build();
        when(stocksRepository.save(any()))
                .thenReturn(Mono.just(stock));
        when(stockMarketClient.publishStock(any()))
                .thenReturn(Mono.just(stockPublishResponse));
        // WHEN
        StepVerifier.create(stocksService.createStock(stockRequest))
        // THEN
                    .verifyError(StockCreationException.class);
    }
}
