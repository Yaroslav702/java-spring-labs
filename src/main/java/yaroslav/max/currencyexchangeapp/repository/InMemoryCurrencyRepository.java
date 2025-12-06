package yaroslav.max.currencyexchangeapp.repository;

import org.springframework.stereotype.Repository;
import yaroslav.max.currencyexchangeapp.entity.Currency;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryCurrencyRepository implements  CurrencyRepository {
    private final List<Currency> currencies = new ArrayList<>();

    public InMemoryCurrencyRepository() {
        currencies.add(new Currency("USD", "US Dollar"));
        currencies.add(new Currency("EUR", "Euro"));
        currencies.add(new Currency("UAH", "Hryvnia"));
    }

    @Override
    public List<Currency> findAll() {
        return new ArrayList<>(currencies);
    }

    @Override
    public Optional<Currency> findByCode(String code) {
        return currencies.stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst();
    }

    @Override
    public void save(Currency currency) {
        findByCode(currency.getCode()).ifPresent(currencies::remove);
        currencies.add(currency);
    }
}
