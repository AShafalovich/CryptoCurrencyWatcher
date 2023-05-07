package com.ashafalovich.cryptoCurrencyWatcher.mapper;

import com.ashafalovich.cryptoCurrencyWatcher.dto.CryptoCurrencyDto;
import com.ashafalovich.cryptoCurrencyWatcher.model.CryptoCurrency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CryptoCurrencyMapper {

    CryptoCurrencyDto toDto(CryptoCurrency cryptoCurrency);
}
