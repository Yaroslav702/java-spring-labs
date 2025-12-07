package yaroslav.max.currencyexchangeapp.repository;

import org.springframework.stereotype.Repository;
import yaroslav.max.currencyexchangeapp.entity.ExchangeRate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InMemoryExchangeRateRepository implements ExchangeRateRepository {
    private final List<ExchangeRate> rates = new ArrayList<>();

    @Override
    public void save(ExchangeRate rate) {
        rates.add(rate);
    }

    public List<ExchangeRate> findByDate(LocalDate date) {
        return rates.stream()
                .filter(r -> r.getDate().equals(date))
                .collect(Collectors.toList());
    }
}
