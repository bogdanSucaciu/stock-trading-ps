package com.pluralsight.springwebflux5.stockmarket.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRate {

    private BigDecimal rate;

    private String currencyName;

}
