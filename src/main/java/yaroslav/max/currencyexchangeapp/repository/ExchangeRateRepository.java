package yaroslav.max.currencyexchangeapp.repository;

import yaroslav.max.currencyexchangeapp.entity.ExchangeRate;
import java.time.LocalDate;
import java.util.List;

public interface ExchangeRateRepository {
    void save(ExchangeRate rate);
    List<ExchangeRate> findByDate(LocalDate date);
}