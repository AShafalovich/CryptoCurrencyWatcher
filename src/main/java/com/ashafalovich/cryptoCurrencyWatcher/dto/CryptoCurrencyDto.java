package com.ashafalovich.cryptoCurrencyWatcher.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CryptoCurrencyDto {

    private Long id;
    private String symbol;
    private Double price;
}
