package com.pluralsight.springwebflux5.stocktrading.it;

import com.pluralsight.springwebflux5.stockmarket.api.currencyrate.CurrencyRate;
import com.pluralsight.springwebflux5.stockmarket.api.exception.ErrorMessage;
import com.pluralsight.springwebflux5.stocktrading.client.StockMarketClient;
import com.pluralsight.springwebflux5.stocktrading.dto.StockResponse;
import com.pluralsight.springwebflux5.stocktrading.model.Stock;
import com.pluralsight.springwebflux5.stocktrading.repository.StocksRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StockIT {

    private static final String STOCK_ID = "621a97f1d11fc40fcdd5c67b";
    private static final String STOCK_NAME = "Globomantics";
    private static final BigDecimal STOCK_PRICE = BigDecimal.TEN;
    private static final String STOCK_CURRENCY = "USD";

    @MockBean
    StocksRepository stocksRepository;

    @MockBean
    StockMarketClient stockMarketClient;

    @Autowired
    WebTestClient client;

    @Test
    void shouldGetOneStock() {
        // GIVEN

        // WHEN

        // THEN

    }
    @Test
    void shouldGetOneStock() {
        Stock stock = Stock.builder()
                .id(STOCK_ID)
                .name(STOCK_NAME)
                .price(STOCK_PRICE)
                .currency(STOCK_CURRENCY)
                .build();
        CurrencyRate currencyRate = CurrencyRate.builder()
                .currencyName("USD")
                .rate(BigDecimal.ONE)
                .build();
        when(stocksRepository.findById(STOCK_ID))
                .thenReturn(Mono.just(stock));
        when(stockMarketClient.getCurrencyRates())
                .thenReturn(Flux.just(currencyRate));

        StockResponse stockResponse = client.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/stocks/{id}")
                                .build(STOCK_ID))
                .exchange()
                .expectStatus().isOk()
                .expectBody(StockResponse.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(stockResponse);
        assertEquals(stockResponse.getId(), STOCK_ID);
        assertEquals(stockResponse.getName(), STOCK_NAME);
        assertEquals(stockResponse.getPrice(), STOCK_PRICE);
        assertEquals(stockResponse.getCurrency(), STOCK_CURRENCY);
    }

    @Test
    void shouldReturnNotFoundWhenGetOneStock() {
        // GIVEN

        // WHEN

        // THEN

    }
    @Test
    void shouldReturnNotFoundWhenGetOneStock() {
        when(stocksRepository.findById(STOCK_ID))
                .thenReturn(Mono.empty());

        ErrorMessage errorMessage = client.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/stocks/{id}")
                                .build(STOCK_ID))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        assertTrue(errorMessage.getMessage().contains("Stock not found"));
    }
}
