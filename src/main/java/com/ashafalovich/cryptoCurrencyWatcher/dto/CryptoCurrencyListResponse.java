package com.ashafalovich.cryptoCurrencyWatcher.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CryptoCurrencyListResponse extends Response{

    private List<CryptoCurrencyDto> data;
}
