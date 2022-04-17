package com.pluralsight.springwebflux5.stocktrading.service;


import com.pluralsight.springwebflux5.stocktrading.model.Stock;
import com.pluralsight.springwebflux5.stocktrading.repository.StocksRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class StocksService {

    private StocksRepository stocksRepository;

    public Optional<Stock> getOneStock(String id) {
        return stocksRepository.findById(id);
    }

    public Iterable<Stock> getAllStocks() {
        return stocksRepository.findAll();
    }

    public Stock createStock(Stock stock) {
        return stocksRepository.save(stock);
    }
}
