package com.adepuu.montrackbackend.currency.repository;

import com.adepuu.montrackbackend.currency.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepo extends JpaRepository<Currency, Integer>  {
}
