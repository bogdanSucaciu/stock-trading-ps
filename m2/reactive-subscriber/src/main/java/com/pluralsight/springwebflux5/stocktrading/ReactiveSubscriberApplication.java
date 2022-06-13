package com.pluralsight.springwebflux5.stocktrading;

import com.pluralsight.springwebflux5.stocktrading.client.ReactiveStocksSubscriber;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class ReactiveSubscriberApplication {

	private ReactiveStocksSubscriber reactiveStocksSubscriber;

	public static void main(String[] args) {
		SpringApplication.run(ReactiveSubscriberApplication.class, args);
	}

	@PostConstruct
	public void subscribeToStockTradingApp() {
		reactiveStocksSubscriber.getOneStock("1")
				.block();

		log.info("*******************************************************************************************");

		reactiveStocksSubscriber.getAllStocks()
				.blockLast();
	}

}
