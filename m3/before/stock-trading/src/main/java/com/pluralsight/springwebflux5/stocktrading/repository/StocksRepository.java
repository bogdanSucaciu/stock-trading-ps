package com.pluralsight.springwebflux5.stocktrading.repository;

import com.pluralsight.springwebflux5.stocktrading.model.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StocksRepository extends MongoRepository<Stock, String> {
}
