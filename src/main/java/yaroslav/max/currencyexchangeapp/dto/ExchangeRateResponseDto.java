package yaroslav.max.currencyexchangeapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExchangeRateResponseDto(
        Long id,
        String sourceCurrencyCode,
        String targetCurrencyCode,
        LocalDate date,
        BigDecimal rate
) {}