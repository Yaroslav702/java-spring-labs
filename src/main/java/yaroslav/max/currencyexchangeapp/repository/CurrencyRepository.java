package yaroslav.max.currencyexchangeapp.repository;

import yaroslav.max.currencyexchangeapp.entity.Currency;
import java.util.List;
import java.util.Optional;

public interface CurrencyRepository {
    void save(Currency currency);
    List<Currency> findAll();
    Optional<Currency> findByCode(String code);
}
