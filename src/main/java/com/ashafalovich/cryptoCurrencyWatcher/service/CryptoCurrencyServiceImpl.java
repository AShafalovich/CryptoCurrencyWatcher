package com.ashafalovich.cryptoCurrencyWatcher.service;

import com.ashafalovich.cryptoCurrencyWatcher.dto.*;
import com.ashafalovich.cryptoCurrencyWatcher.mapper.CryptoCurrencyMapper;
import com.ashafalovich.cryptoCurrencyWatcher.mapper.UserMapper;
import com.ashafalovich.cryptoCurrencyWatcher.model.Tickers;
import com.ashafalovich.cryptoCurrencyWatcher.model.CryptoCurrency;
import com.ashafalovich.cryptoCurrencyWatcher.model.User;
import com.ashafalovich.cryptoCurrencyWatcher.repository.CryptoCurrencyRepository;
import com.ashafalovich.cryptoCurrencyWatcher.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.*;
import static org.springframework.http.HttpStatus.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {

    @Value("${crypto.currency.baseUrl}")
    private String BASE_URL;

    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final UserMapper userMapper;
    private final CryptoCurrencyMapper cryptoCurrencyMapper;

    @PostConstruct
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void updateCryptoCurrenciesAndCheckPriceChanges() {
        ResponseEntity<Tickers> response = restTemplate.getForEntity(BASE_URL, Tickers.class);
        if (null != response.getBody()) {
            List<CryptoCurrency> cryptoCurrencies = response.getBody().getData();
            cryptoCurrencyRepository.saveAll(cryptoCurrencies);
        } else {
            log.error("Error while getting data from the server");
        }

        List<User> allUsers = userRepository.findAll();
        List<String> symbols = allUsers.stream()
                .map(User::getSymbol)
                .map(String::toUpperCase)
                .collect(toList());
        List<CryptoCurrency> cryptoCurrencies = cryptoCurrencyRepository.findBySymbolIn(symbols);
        Map<String, CryptoCurrency> mapCryptoCurrency = cryptoCurrencies.stream()
                .collect(toMap(CryptoCurrency::getSymbol, Function.identity()));

        allUsers.forEach(user -> {
            CryptoCurrency cryptoCurrency = mapCryptoCurrency.get(user.getSymbol());
            double percentOfPriceChange = calculatePercentOfPriceChange(user, cryptoCurrency);
            if (percentOfPriceChange >= 1) {
                log.warn("\nSymbol: " + user.getSymbol()
                        + "\nUsername: " + user.getName()
                        + "\nPrice changed on " + percentOfPriceChange + " percents\n");
            }
        });
    }

    private double calculatePercentOfPriceChange(User user, CryptoCurrency cryptoCurrency) {
        double initialPrice = user.getPrice();
        double updatedPrice = cryptoCurrency.getPrice();
        return Math.abs((initialPrice - updatedPrice) / initialPrice) * 100;
    }

    @Override
    public Response findAll() {
        List<CryptoCurrency> cryptoCurrencies = cryptoCurrencyRepository.findAll();
        List<CryptoCurrencyDto> cryptoCurrencyDtos = cryptoCurrencies.stream()
                .map(cryptoCurrencyMapper::toDto)
                .collect(toList());
        return new CryptoCurrencyListResponse(cryptoCurrencyDtos);
    }

    @Override
    public Response findBySymbol(String symbol) {
        Response result;
        if (symbol != null) {
            Optional<CryptoCurrency> cryptoCurrencyOptional = cryptoCurrencyRepository
                    .findBySymbol(symbol.toUpperCase());
            if (cryptoCurrencyOptional.isPresent()) {
                CryptoCurrency cryptoCurrency = cryptoCurrencyOptional.get();
                result = new CryptoCurrencyResponse(cryptoCurrency.getPrice());
            } else {
                result = getResponseWithErrorMessage(NOT_FOUND);
                log.warn("Symbol {} not found", symbol);
            }
        } else {
            result = getResponseWithErrorMessage(BAD_REQUEST);
            log.error("Symbol is null");
        }
        return result;
    }

    @Override
    public Response notify(UserRequest userDto) {
        Response result;
        if (null != userDto.getName() && null != userDto.getSymbol()) {
            Optional<CryptoCurrency> cryptoCurrencyOptional = cryptoCurrencyRepository.findBySymbol(userDto.getSymbol().toUpperCase());
            if (cryptoCurrencyOptional.isPresent()) {
                Double price = cryptoCurrencyOptional.get().getPrice();
                User user = userMapper.toUser(userDto);
                user.setPrice(price);
                User savedUser = userRepository.save(user);
                result = userMapper.toResponse(savedUser);
            } else {
                result = getResponseWithErrorMessage(BAD_REQUEST);
                log.warn("Unable to find the cryptocurrency for symbol {}", userDto.getSymbol());
            }
        } else {
            result = getResponseWithErrorMessage(BAD_REQUEST);
            log.error("The request contains invalid data. Username: {}, Symbol: {}", userDto.getName(), userDto.getSymbol());
        }
        return result;
    }

    private Response getResponseWithErrorMessage(HttpStatus status) {
        return Response.builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .build();
    }
}