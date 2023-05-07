package com.ashafalovich.cryptoCurrencyWatcher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "cryptocurrencies")
public class CryptoCurrency {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "symbol")
    private String symbol;

    @JsonProperty("price_usd")
    @Column(name = "price")
    private Double price;

}
