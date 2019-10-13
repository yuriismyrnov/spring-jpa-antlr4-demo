package com.levi9.demo.wallet.repository;

import com.levi9.demo.wallet.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
