package com.pluralsight.springwebflux5.stocktrading.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ReactiveStocksSubscriber {

    private WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080").build();

    public Mono<Stock> getOneStock(String id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/stocks/{id}")
                        .build(id))
                .retrieve()
                .bodyToMono(Stock.class)
                .doOnSubscribe(subscription -> log.info("On Subscribe"))
                .doOnNext(stock -> log.info("On Next stock: {}", stock))
                .doFinally(x -> log.info("On Complete"));
    }

    public Flux<Stock> getAllStocks() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/stocks")
                        .build())
                .retrieve()
                .bodyToFlux(Stock.class)
                .doOnSubscribe(subscription -> log.info("On Subscribe"))
                .doOnNext(stock -> log.info("On Next stock: {}", stock))
                .doOnComplete(() -> log.info("On Complete"));
    }
}
