package com.ashafalovich.cryptoCurrencyWatcher.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserRequest {

    private String name;
    private String symbol;

}
