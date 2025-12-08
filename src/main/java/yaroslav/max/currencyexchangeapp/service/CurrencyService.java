package yaroslav.max.currencyexchangeapp.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import yaroslav.max.currencyexchangeapp.dto.CurrencyRequestDto;
import yaroslav.max.currencyexchangeapp.entity.Currency;
import yaroslav.max.currencyexchangeapp.repository.CurrencyRepository;

import java.util.Optional;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Currency createCurrency(CurrencyRequestDto dto) {
        Currency currency = new Currency();
        currency.setCode(dto.code().toUpperCase());
        currency.setDisplayName(dto.displayName());
        return currencyRepository.save(currency);
    }

    public Optional<Currency> getCurrencyById(Long id) {
        return currencyRepository.findById(id);
    }

    public Page<Currency> getAllCurrencies(String filterCode, Pageable pageable) {
        Page<Currency> result;

        if (filterCode != null && !filterCode.isEmpty()) {
            result = currencyRepository.findByCodeContainingIgnoreCase(filterCode, pageable);
        }
        else {
            result = currencyRepository.findAll(pageable);
        }

        return result;
    }

    public Currency updateCurrency(Long id, CurrencyRequestDto dto) {
        Currency currency = this.getCurrencyById(id)
                .orElseThrow(() -> new EntityNotFoundException("Currency not found"));

        currency.setCode(dto.code().toUpperCase());
        currency.setDisplayName(dto.displayName());

        return currencyRepository.save(currency);
    }

    public Currency patchCurrency(Long id, CurrencyRequestDto dto) {
        Currency currency = getCurrencyById(id)
                .orElseThrow(() -> new EntityNotFoundException("Currency not found"));

        if (dto.code() != null) currency.setCode(dto.code().toUpperCase());
        if (dto.displayName() != null) currency.setDisplayName(dto.displayName());

        return currencyRepository.save(currency);
    }

    public void deleteCurrency(Long id) {
        currencyRepository.deleteById(id);
    }
}