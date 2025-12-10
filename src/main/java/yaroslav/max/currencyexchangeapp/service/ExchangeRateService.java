package yaroslav.max.currencyexchangeapp.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yaroslav.max.currencyexchangeapp.dto.ExchangeRateRequestDto;
import yaroslav.max.currencyexchangeapp.dto.ExchangeRateResponseDto;
import yaroslav.max.currencyexchangeapp.entity.Currency;
import yaroslav.max.currencyexchangeapp.entity.ExchangeRate;
import yaroslav.max.currencyexchangeapp.repository.CurrencyRepository;
import yaroslav.max.currencyexchangeapp.repository.ExchangeRateRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;

    @Transactional
    public ExchangeRateResponseDto createExchangeRate(ExchangeRateRequestDto dto) {
        Currency source = currencyRepository.findById(dto.sourceCurrencyId())
                .orElseThrow(() -> new EntityNotFoundException("Source Currency not found"));

        Currency target = currencyRepository.findById(dto.targetCurrencyId())
                .orElseThrow(() -> new EntityNotFoundException("Target Currency not found"));

        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setSourceCurrency(source);
        exchangeRate.setTargetCurrency(target);
        exchangeRate.setDate(dto.date());
        exchangeRate.setRate(dto.rate());

        ExchangeRate savedRate = exchangeRateRepository.save(exchangeRate);
        return mapToResponseDto(savedRate);
    }

    public Page<ExchangeRateResponseDto> getRatesBySourceCurrency(String code, Pageable pageable) {
        return exchangeRateRepository.findBySourceCurrency_Code(code, pageable)
                .map(this::mapToResponseDto);
    }

    public List<ExchangeRateResponseDto> getRatesByTargetCurrency(String code) {
        return exchangeRateRepository.findByTargetCurrencyCode(code).stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    public List<ExchangeRateResponseDto> getRatesByDate(LocalDate date) {
        return exchangeRateRepository.findByDateNamed(date).stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    protected ExchangeRateResponseDto mapToResponseDto(ExchangeRate entity) {
        return new ExchangeRateResponseDto(
                entity.getId(),
                entity.getSourceCurrency().getCode(),
                entity.getTargetCurrency().getCode(),
                entity.getDate(),
                entity.getRate()
        );
    }
}