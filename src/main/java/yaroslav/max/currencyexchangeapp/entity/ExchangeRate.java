package yaroslav.max.currencyexchangeapp.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class ExchangeRate {
    private Currency sourceCurrency;
    private Currency targetCurrency;
    private LocalDate date;
    private BigDecimal rate;
}
