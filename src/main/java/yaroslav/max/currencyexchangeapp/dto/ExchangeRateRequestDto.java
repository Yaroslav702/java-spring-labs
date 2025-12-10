package yaroslav.max.currencyexchangeapp.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ExchangeRateRequestDto(
        @NotNull(message = "Source currency ID is required")
        Long sourceCurrencyId,

        @NotNull(message = "Target currency ID is required")
        Long targetCurrencyId,

        @NotNull(message = "Date is required")
        LocalDate date,

        @NotNull(message = "Rate is required")
        @DecimalMin(value = "0.0001", message = "Rate must be positive")
        BigDecimal rate
) {}