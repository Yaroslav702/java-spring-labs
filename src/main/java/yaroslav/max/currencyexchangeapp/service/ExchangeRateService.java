package yaroslav.max.currencyexchangeapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yaroslav.max.currencyexchangeapp.entity.ExchangeRate;
import yaroslav.max.currencyexchangeapp.repository.ExchangeRateRepository;

@Service
public class ExchangeRateService {
    private ExchangeRateRepository exchangeRateRepository;

    // Setter Injection (Demo)
    @Autowired
    public void setExchangeRateRepository(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public void addRate(ExchangeRate rate) {
        exchangeRateRepository.save(rate);
    }
}
