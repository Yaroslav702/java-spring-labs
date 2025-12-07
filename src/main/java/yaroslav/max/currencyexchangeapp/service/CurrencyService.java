package yaroslav.max.currencyexchangeapp.service;

import org.springframework.stereotype.Service;
import yaroslav.max.currencyexchangeapp.entity.Currency;
import yaroslav.max.currencyexchangeapp.repository.CurrencyRepository;

import java.util.List;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    // Constructor Injection
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public void createCurrency(String code, String name) {
        currencyRepository.save(new Currency(code, name));
    }

    public Currency getCurrency(String code) {
        return currencyRepository.findByCode(code).orElse(null);
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }
}