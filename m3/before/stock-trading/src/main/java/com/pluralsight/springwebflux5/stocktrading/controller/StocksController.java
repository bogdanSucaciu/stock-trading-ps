package com.pluralsight.springwebflux5.stocktrading.controller;

import com.pluralsight.springwebflux5.stocktrading.model.Stock;
import com.pluralsight.springwebflux5.stocktrading.service.StocksService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/stocks")
public class StocksController {

    private StocksService stocksService;

    @GetMapping("/{id}")
    public Optional<Stock> getOneStock(@PathVariable String id) {
        return stocksService.getOneStock(id);
    }

    @GetMapping
    public Iterable<Stock> getAllStocks() {
        return stocksService.getAllStocks();
    }

    @PostMapping
    public Stock createStock(@RequestBody Stock stock) {
        return stocksService.createStock(stock);
    }

}
