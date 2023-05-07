package com.ashafalovich.cryptoCurrencyWatcher.repository;

import com.ashafalovich.cryptoCurrencyWatcher.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
