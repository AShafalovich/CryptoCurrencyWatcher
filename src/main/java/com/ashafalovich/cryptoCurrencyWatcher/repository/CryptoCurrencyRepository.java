package com.ashafalovich.cryptoCurrencyWatcher.repository;

import com.ashafalovich.cryptoCurrencyWatcher.model.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Long> {

    Optional<CryptoCurrency> findBySymbol(String symbol);

    List<CryptoCurrency> findBySymbolIn(List<String> symbol);
}
