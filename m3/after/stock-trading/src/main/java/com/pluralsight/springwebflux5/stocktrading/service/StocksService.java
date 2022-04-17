package com.pluralsight.springwebflux5.stocktrading.service;


import java.math.BigDecimal;

import com.pluralsight.springwebflux5.stocktrading.dto.StockRequest;
import com.pluralsight.springwebflux5.stocktrading.dto.StockResponse;
import com.pluralsight.springwebflux5.stocktrading.exception.StockCreationException;
import com.pluralsight.springwebflux5.stocktrading.exception.StockNotFoundException;
import com.pluralsight.springwebflux5.stocktrading.repository.StocksRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;



@Service
@Slf4j
@AllArgsConstructor
public class StocksService {

    private StocksRepository stocksRepository;

    public Mono<StockResponse> getOneStock(String id) {
        return stocksRepository.findById(id)
                .map(StockResponse::fromModel)
                .switchIfEmpty(Mono.error(
                    new StockNotFoundException(
                        "Stock not found with id: " + id)))
                        .doFirst(() -> log.info("Retrieving stock with id: {}", id))
                        .doOnNext(stock -> log.info("Stock found: {}", stock))
                        .doOnError(ex -> log.error("Something went wrong while retrieving the stock with id: {}", id, ex))
                        .doOnTerminate(() -> log.info("Finalized retrieving stock"))
                        .doFinally(signalType -> log.info("Finalized retrieving stock with signal type: {}", signalType));        
    }

    public Flux<StockResponse> getAllStocks(BigDecimal priceGreaterThan) {
        return stocksRepository.findAll()
                .filter(stock -> 
                    stock.getPrice().compareTo(priceGreaterThan) > 0)
                .map(StockResponse::fromModel)
                .doFirst(() -> log.info("Retrieving all stocks"))
                .doOnNext(stock -> log.info("Stock found: {}", stock))
                .doOnError(ex -> log.warn("Something went wrong while retrieving the stocks", ex))
                .doOnTerminate(() -> log.info("Finalized retrieving stocks"))
                .doFinally(signalType -> log.info("Finalized retrieving stock with signal type: {}", signalType));
    }

    public Mono<StockResponse> createStock(StockRequest stockRequest) {
        return Mono.just(stockRequest)
                .map(StockRequest::toModel)
                .flatMap(stock -> stocksRepository.save(stock))
                .map(StockResponse::fromModel)
                .onErrorMap(ex -> new StockCreationException(ex.getMessage()));
    }
}
