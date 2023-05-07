package com.ashafalovich.cryptoCurrencyWatcher.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends Response {

    private Long id;
    private String name;
    private String symbol;
    private Double price;

}
