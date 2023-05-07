package com.ashafalovich.cryptoCurrencyWatcher.controller;

import com.ashafalovich.cryptoCurrencyWatcher.dto.UserRequest;
import com.ashafalovich.cryptoCurrencyWatcher.service.CryptoCurrencyService;
import com.ashafalovich.cryptoCurrencyWatcher.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CryptoCurrencyController {

    private final CryptoCurrencyService cryptoCurrencyService;

    @GetMapping("/tickers")
    public ResponseEntity<Object> findAll() {
        return ResponseUtils.answer(cryptoCurrencyService.findAll());
    }

    @GetMapping("/tickers/{symbol}")
    public ResponseEntity<Object> findBySymbol(@PathVariable String symbol) {
        return ResponseUtils.answer(cryptoCurrencyService.findBySymbol(symbol));
    }

    @PostMapping("/notify")
    public ResponseEntity<Object> notify(@RequestBody UserRequest user) {
        return ResponseUtils.answer(cryptoCurrencyService.notify(user));
    }
}

