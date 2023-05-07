package com.ashafalovich.cryptoCurrencyWatcher.service;

import com.ashafalovich.cryptoCurrencyWatcher.dto.Response;
import com.ashafalovich.cryptoCurrencyWatcher.dto.UserRequest;


public interface CryptoCurrencyService {

    Response findAll();

    Response findBySymbol(String symbol);

    Response notify(UserRequest user);
}
